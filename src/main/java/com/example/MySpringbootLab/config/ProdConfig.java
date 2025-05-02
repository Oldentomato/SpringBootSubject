package com.example.MySpringbootLab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("prod")
public class ProdConfig {
    @Bean
    public MyEnviornment myenv(){
        return MyEnviornment.builder().mode("운영환경").build();
    }
}
