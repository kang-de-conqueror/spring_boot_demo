package com.example.demo.dto;

import com.example.demo.model.Account;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse extends AccountUser {

    @JsonProperty("token")
    private String token;

    public LoginResponse(Account account) {
        super(account);
    }
}
