package com.example.demo.service;

import com.example.demo.dto.BaseResponse;
import com.example.demo.model.AccountLogin;
import com.example.demo.model.AccountRegister;

public interface AccountService {
    BaseResponse login(AccountLogin accountLogin);
    BaseResponse register(AccountRegister accountRegister);
}
