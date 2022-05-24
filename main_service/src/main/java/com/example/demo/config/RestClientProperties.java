package com.example.demo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(value = "conneto.rest.client")
@PropertySource("classpath:common-config.properties")
@Validated
@Getter
@Setter
public class RestClientProperties {

    /**
     * The timeout in ms when attempting to connect to a remote host. 0 means infinite.
     */
    private int connectionTimeout;

    /**
     * The timeout in ms when requesting a connection from the connection manager. 0 means infinite.
     */
    private int connectionRequestTimeout;

    /**
     * The timeout in ms when trying to read data from the socket (connection already established). 0 means infinite.
     */
    private int readTimeout;

    /**
     * The max limit of connections in total.
     */
    private int maxTotal;

    /**
     * The default max limit of connections per route.
     */
    private int defaultMaxPerRoute;
}
