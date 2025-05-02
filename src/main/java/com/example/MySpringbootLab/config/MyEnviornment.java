package com.example.MySpringbootLab.config;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@ToString
public class MyEnviornment {
    private String mode;
}
