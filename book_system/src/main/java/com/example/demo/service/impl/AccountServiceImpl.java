package com.example.demo.service.impl;

import com.example.demo.constant.ResultCode;
import com.example.demo.constant.Status;
import com.example.demo.dto.BaseResponse;
import com.example.demo.model.AccountLogin;
import com.example.demo.model.AccountRegister;
import com.example.demo.service.AccountService;
import com.example.demo.service.HttpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccountServiceImpl implements AccountService {

    // Service
    private final HttpService httpService;

    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    private static final String API_AUTHENTICATION = "http://localhost:5000/authentication/api/v1";

    @Override
    public BaseResponse login(AccountLogin accountLogin) {
        String url = String.format("%s/%s", API_AUTHENTICATION, "accounts/login");

        HttpEntity<Object> httpEntity = httpService.post(url, accountLogin);

        if (ObjectUtils.isEmpty(httpEntity)) {
            return new BaseResponse(Status.ERROR, Status.getMessage(Status.ERROR, null, messageSource));
        }

        BaseResponse baseResponse = objectMapper.convertValue(httpEntity.getBody(), BaseResponse.class);

        return baseResponse;
    }

    @Override
    public BaseResponse register(AccountRegister accountRegister) {
        String url = String.format("%s/%s", API_AUTHENTICATION, "accounts/register");

        HttpEntity<Object> httpEntity = httpService.post(url, accountRegister);

        if (ObjectUtils.isEmpty(httpEntity)) {
            return new BaseResponse(Status.ERROR, Status.getMessage(Status.ERROR, null, messageSource));
        }

        BaseResponse baseResponse = objectMapper.convertValue(httpEntity.getBody(), BaseResponse.class);

        return baseResponse;
    }
}
