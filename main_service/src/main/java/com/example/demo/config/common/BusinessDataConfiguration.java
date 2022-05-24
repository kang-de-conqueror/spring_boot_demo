package com.example.demo.config.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Configuration
@PropertySource({"classpath:business_data_config.properties"})
public class BusinessDataConfiguration {
    @Value("${jwt.secret}")
    private String jwtSecret;
}
