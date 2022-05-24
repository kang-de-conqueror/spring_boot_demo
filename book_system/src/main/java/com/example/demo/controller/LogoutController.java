package com.example.demo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LogoutController {

    private final HttpServletRequest request;

    @GetMapping("/logout")
    public String logout() {
        request.getSession().invalidate();
        return "/home";
    }
}
