package com.example.demo.controller;

import com.example.demo.model.BookEntity;
import com.example.demo.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeController {

    // Service
    private final BookService bookService;

    @GetMapping("/home")
    public String homePage(Model model) {
        List<BookEntity> bookEntities = bookService.getAll();

        model.addAttribute("books", bookEntities);

        return "home";
    }
}
