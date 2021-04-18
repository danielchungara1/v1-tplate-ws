package com.tplate.layers.b.business.services;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanServices {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
