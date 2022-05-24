package com.example.demo.service;

import com.example.demo.dto.BaseResponse;
import com.example.demo.model.BookEntity;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<BookEntity> getAll();
    Optional<BookEntity> getById(String id);
    BaseResponse create(BookEntity bookEntity);
    BaseResponse update(String id, BookEntity bookEntity);
    BaseResponse delete(String id);
}
