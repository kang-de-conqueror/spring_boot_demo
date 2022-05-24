package com.example.demo.controller;

import com.example.demo.constant.ResultCode;
import com.example.demo.dto.AccountUser;
import com.example.demo.dto.BaseResponse;
import com.example.demo.model.AccountLogin;
import com.example.demo.service.AccountService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LoginController {

    // Service
    private final AccountService accountService;

    private final HttpServletRequest request;
    private final ObjectMapper objectMapper;

    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("accountLogin", new AccountLogin());
        return "login";
    }

    @PostMapping("/loginForm")
    public String login(@Valid @ModelAttribute(value = "accountLogin") AccountLogin accountLogin, Errors errors, Model model) throws JsonProcessingException {
        if (null != errors && errors.getErrorCount() > 0) {
            return "login";
        }

        BaseResponse baseResponse = accountService.login(accountLogin);
        if (ResultCode.SUCCESS.equals(baseResponse.getStatus())) {
            AccountUser accountUser = objectMapper.convertValue(baseResponse.getData(), AccountUser.class);

            request.getSession().setAttribute("USER", accountUser);
            return "redirect:/home";
        }
        model.addAttribute("loginError", baseResponse.getMessage());

        return "login";
    }

}
