package com.example.demo.controller;

import com.example.demo.constant.ResultCode;
import com.example.demo.dto.BaseResponse;
import com.example.demo.model.AccountRegister;
import com.example.demo.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterController {

    // Service
    private final AccountService accountService;

    @GetMapping("/register")
    public String registerPage(Model model) {
        model.addAttribute("accountRegister", new AccountRegister());
        return "register";
    }

    @PostMapping("/registerForm")
    public String register(@Valid @ModelAttribute("accountRegister") AccountRegister accountRegister, Errors errors, Model model) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "register";
        }

        BaseResponse baseResponse = accountService.register(accountRegister);
        if (ResultCode.SUCCESS.equals(baseResponse.getStatus())) {
            return "login";
        }

        model.addAttribute("registerError", baseResponse.getMessage());

        return "register";
    }
}
