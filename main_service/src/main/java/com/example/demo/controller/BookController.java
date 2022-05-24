package com.example.demo.controller;

import com.example.demo.dto.request.BookRequest;
import com.example.demo.dto.response.BaseResponse;
import com.example.demo.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Slf4j
@RequestMapping("${base-path}/books")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    // Service
    private final BookService bookService;

    private final ObjectMapper objectMapper;

    @GetMapping()
    public ResponseEntity<BaseResponse> getAll() throws JsonProcessingException {
        log.info("START GET----/books");

        long start = System.currentTimeMillis();

        BaseResponse response = bookService.getAll();

        long duration = System.currentTimeMillis() - start;
        log.info("END GET----/books response: {} - run `{}`ms", objectMapper.writeValueAsString(response), duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse> getById(@PathVariable("id") Integer id) throws JsonProcessingException {
        log.info("START GET----/books/{}", id);

        long start = System.currentTimeMillis();

        BaseResponse response = bookService.getById(id);

        long duration = System.currentTimeMillis() - start;
        log.info("END GET----/books/{} response: {} - run `{}`ms", id, objectMapper.writeValueAsString(response), duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    @PreAuthorize("hasAnyAuthority('member')")
    public ResponseEntity<BaseResponse> create(@Valid @RequestBody BookRequest bookRequest) throws JsonProcessingException {
        log.info("START POST----/books: {}", objectMapper.writeValueAsString(bookRequest));

        long start = System.currentTimeMillis();

        BaseResponse response = bookService.create(bookRequest);

        long duration = System.currentTimeMillis() - start;
        log.info("END POST----/books response: {} - run `{}`ms", objectMapper.writeValueAsString(response), duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('member')")
    public ResponseEntity<BaseResponse> update(@PathVariable("id") String id, @Valid @RequestBody BookRequest bookRequest) throws JsonProcessingException {
        log.info("START PUT----/books/{}: {}", id, objectMapper.writeValueAsString(bookRequest));

        long start = System.currentTimeMillis();

        BaseResponse response = bookService.update(id, bookRequest);

        long duration = System.currentTimeMillis() - start;
        log.info("END PUT----/books/{} response: {} - run `{}`ms", id, objectMapper.writeValueAsString(response), duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('member')")
    public ResponseEntity<BaseResponse> delete(@PathVariable("id") String id) throws JsonProcessingException {
        log.info("START DELETE----/books/{}", id);

        long start = System.currentTimeMillis();

        BaseResponse response = bookService.delete(id);

        long duration = System.currentTimeMillis() - start;
        log.info("END DELETE----/books/{} response: {} - run `{}`ms", id, objectMapper.writeValueAsString(response), duration);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
