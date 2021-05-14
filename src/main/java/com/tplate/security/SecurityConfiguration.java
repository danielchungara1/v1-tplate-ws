package com.tplate.security;

import com.tplate.security.jwt.JwtExceptionHandlerFilter;
import com.tplate.security.jwt.JwtAuthorizationFilter;
import com.tplate.layers.access.shared.Endpoints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;


@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtAuthorizationFilter jwtAuthorizationFilter;

    @Autowired
    UserDetailsServiceImpl jwtUserDetailsService;

    @Autowired
    JwtExceptionHandlerFilter jwtExceptionHandlerFilter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // configure AuthenticationManager so that it knows from where to load
        // user for matching credentials
        // Use BCryptPasswordEncoder
        auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We don't need CSRF for this example
        http.cors().and().csrf().disable();

        // Allow same origin for X-Frame-Options on the server
        http.headers().frameOptions().sameOrigin();

        //Permit request from diferent origins, equivalent to: @CrossOrigin
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());

        //Sessions are management by token
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //Public and private Paths
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "*").permitAll()
                .antMatchers(Endpoints.AUTH + "/*").permitAll()
                // Disallow everything else..
                .antMatchers(Endpoints.USER + "*").authenticated();
        //Apply JWT
        http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(jwtExceptionHandlerFilter, JwtAuthorizationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
