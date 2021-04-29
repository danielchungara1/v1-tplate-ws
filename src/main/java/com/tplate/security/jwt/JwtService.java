package com.tplate.security.jwt;

import com.tplate.layers.persistence.models.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class JwtService {

    @Value("${jwt2.expiration-minutes}")
    private Integer EXPIRATION_MINUTES;

    @Value("${jwt2.secret}")
    private String JWT_SECRET_KEY_;
    private static String JWT_SECRET_KEY;

    private static final String AUTHORIZATION_BEAR_PREFIX = "Bearer ";

    @PostConstruct
    protected void init(){
        JWT_SECRET_KEY = Base64.getEncoder().encodeToString(JWT_SECRET_KEY_.getBytes());
    }

    public String generateToken(User user) {

        return Jwts.builder()
                .setSubject(user.getId().toString())
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(EXPIRATION_MINUTES)))
                .signWith(SignatureAlgorithm.HS256, JWT_SECRET_KEY)
                .compact();
    }

    public String subjectFromBearerHeader(String authorizationHeader) throws JwtException {

        String subject;

        try {
            subject = Jwts.parser()
                    .setSigningKey(JWT_SECRET_KEY)
                    .parseClaimsJws(this.tokenFromBearerHeader(authorizationHeader))
                    .getBody()
                    .getSubject();

        }catch (RuntimeException e) {
            log.error("Jwt exception. " + e.getMessage());
            throw new JwtException(e.getMessage());

        }

        return subject;

    }

    private String tokenFromBearerHeader(String authorizationHeader) {

        if (authorizationHeader == null || !authorizationHeader.startsWith(AUTHORIZATION_BEAR_PREFIX)) {
            throw new JwtException("Invalid bearer token.");

        }
        return authorizationHeader.substring(AUTHORIZATION_BEAR_PREFIX.length());

    }


//    public String getUsernameFromToken(String token) {
//        return getClaimFromToken(token, Claims::getSubject);
//    }
//
//    public Date getExpirationDateFromToken(String token) {
//        return getClaimFromToken(token, Claims::getExpiration);
//    }
//
//    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = getAllClaimsFromToken(token);
//        return claimsResolver.apply(claims);
//    }
//
//    private Claims getAllClaimsFromToken(String token) {
//        return Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET_KEY).parseClaimsJws(token).getBody();
//    }
//
//    private Boolean isTokenExpired(String token) {
//        final Date expiration = getExpirationDateFromToken(token);
//        return expiration.before(new Date());
//    }
//
//    private Boolean ignoreTokenExpiration(String token) {
//        // here you specify tokens, for that the expiration is ignored
//        return false;
//    }
//
//    private String doGenerateToken(Map<String, Object> claims, String subject) {
//
//        return Jwts.builder()
//                .setClaims(claims)
//                .setSubject(subject)
//                .setIssuedAt(new Date(System.currentTimeMillis()))
//                .setExpiration(new Date(System.currentTimeMillis() + TimeUtil.toMiliseconds(new Minutes(SecurityConstants.JWT_EXPIRATION_TIME_MINUTES))))
//                .signWith(SignatureAlgorithm.HS512, SecurityConstants.JWT_SECRET_KEY).compact();
//    }
//
//    public Boolean canTokenBeRefreshed(String token) {
//        return (!isTokenExpired(token) || ignoreTokenExpiration(token));
//    }
//
//    public Boolean validateToken(String token, UserDetails userDetails) {
//        Jwts.parser().setSigningKey(SecurityConstants.JWT_SECRET_KEY).parseClaimsJws(token);
//
//        final String username = getUsernameFromToken(token);
//        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
//    }
//
//    public String resolveToken(HttpServletRequest req) {
//        String bearerToken = req.getHeader(SecurityConstants.JWT_HEADER_AUTHORIZATION_KEY);
//        if (bearerToken != null && bearerToken.startsWith(SecurityConstants.JWT_TOKEN_BEAR_PREFIX)) {
//            return bearerToken.substring(7);
//        }
//        return null;
//    }
//
//    private Collection<? extends GrantedAuthority>  getAuthorities(User user) {
//        return user.getRole().getPermissions();
//
//    }
}
