package com.example.demo.controller;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.BaseResponse;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("${base-path}/accounts")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountController {

    // Service
    private final AccountService accountService;

    private final ObjectMapper objectMapper;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest loginRequest) throws Exception {
        log.info("START POST----/accounts/login: {}", loginRequest.getUsername());
        long start = System.currentTimeMillis();

        BaseResponse response = accountService.login(loginRequest);

        long duration = System.currentTimeMillis() - start;
        log.info("END POST----/accounts/login response: {} - run `{}`ms", objectMapper.writeValueAsString(response), duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody RegisterRequest registerRequest) throws Exception {
        log.info("START POST----/accounts/register");
        long start = System.currentTimeMillis();

        BaseResponse response = accountService.register(registerRequest);

        long duration = System.currentTimeMillis() - start;
        log.info("END POST----/accounts/register response: {} - run `{}`ms", objectMapper.writeValueAsString(response), duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
