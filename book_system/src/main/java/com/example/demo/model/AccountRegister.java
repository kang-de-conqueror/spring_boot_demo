package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AccountRegister {
    @NotNull(message = "Please input your full name")
    @NotEmpty(message = "Please input your full name")
    private String fullname;

    @NotNull(message = "Please input your username")
    @NotEmpty(message = "Please input your username")
    private String username;

    @NotNull(message = "Please input your password")
    @NotEmpty(message = "Please input your password")
    private String password;
}
