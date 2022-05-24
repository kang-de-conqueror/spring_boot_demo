package com.example.demo.service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.BaseResponse;
import com.example.demo.dto.RegisterRequest;

import java.sql.SQLIntegrityConstraintViolationException;

public interface AccountService {
    BaseResponse login(LoginRequest loginRequest) throws Exception;
    BaseResponse register(RegisterRequest registerRequest) throws SQLIntegrityConstraintViolationException;
}
