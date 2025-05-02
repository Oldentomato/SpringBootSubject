package com.example.MySpringbootLab.runner;

import com.example.MySpringbootLab.config.MyEnviornment;
import com.example.MySpringbootLab.property.MyPropProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MyPropRunner implements ApplicationRunner {
    @Value("${myprop.username}")
    private String userName;

    @Value("${myprop.port}")
    private int port;

    @Autowired
    private MyPropProperties properties;

    @Autowired
    private MyEnviornment env;


    private Logger logger = LoggerFactory.getLogger(MyPropRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception{
        logger.debug("myprop userName: {}", userName);
        logger.debug("myprop port: {}", port);

        logger.debug("MyPropProperties getUserName() = {}", properties.getUserName());
        logger.debug("MyPropProperties getPort() = {}", properties.getPort());

        logger.info("현재 환경: {}",env.getMode());

    }
}
