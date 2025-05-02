package com.example.MySpringbootLab.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig {
    @Bean
    public MyEnviornment myenv(){
        return MyEnviornment.builder().mode("테스트환경").build();
    }
}
