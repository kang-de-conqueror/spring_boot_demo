package com.example.demo.controller;

import com.example.demo.constant.ResultCode;
import com.example.demo.dto.BaseResponse;
import com.example.demo.model.BookEntity;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
@PreAuthorize("hasAnyAuthority('member')")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    // Service
    private final BookService bookService;

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    @GetMapping("/createBook")
    public String createBookPage(Model model) {
        model.addAttribute("book", new BookEntity());
        return "create_book";
    }

    @PostMapping("/createBookForm")
    public String createBook(@Valid @ModelAttribute(value = "book") BookEntity bookEntity, Errors errors, Model model) {
        if (null != errors && errors.getErrorCount() > 0) {
            return "create_book";
        }
        BaseResponse baseResponse = bookService.create(bookEntity);
        if (ResultCode.SUCCESS.equals(baseResponse.getStatus())) {
            return "redirect:/home";
        }
        model.addAttribute("createBookError", baseResponse.getMessage());

        return "create_book";
    }

    @GetMapping("/updateBook/{id}")
    public String updateBookPage(@PathVariable("id") String id, Model model) throws JsonProcessingException {
        Optional<BookEntity> optionalBookEntity = bookService.getById(id);

        if (!optionalBookEntity.isPresent()) {
            return "redirect:/home";
        }
        model.addAttribute("book", optionalBookEntity.get());

        return "update_book";
    }

    @PostMapping("/updateBookForm")
    public String updateBook(@Valid @ModelAttribute("book") BookEntity bookEntity, Model model) {
        BaseResponse baseResponse = bookService.update(bookEntity.getId(), bookEntity);

        if (ResultCode.SUCCESS.equals(baseResponse.getStatus())) {
            return "redirect:/home";
        }

        return String.format("%s/%s", "redirect:/updateBook", bookEntity.getId());
    }

    @PostMapping("/deleteBook/{id}")
    public String deleteBook(@PathVariable("id") String id, Model model) throws JsonProcessingException {
        BaseResponse baseResponse = bookService.delete(id);
        if (ResultCode.SUCCESS.equals(baseResponse.getStatus())) {
            return "redirect:/home";
        }

        model.addAttribute("deleteBookError", baseResponse.getMessage());

        return "home";
    }
}
