package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @GetMapping(value = {"", "/"})
    public ModelAndView indexPage() {
        return new ModelAndView("redirect:/home");
    }

    @GetMapping("/403")
    public String accessDeniedPage() {
        return "/error/403";
    }
}
