package com.example.demo.service;

import com.example.demo.dto.request.BookRequest;
import com.example.demo.dto.response.BaseResponse;

public interface BookService {
    BaseResponse create(BookRequest bookRequest);
    BaseResponse update(String bookId, BookRequest bookRequest);
    BaseResponse delete(String bookId);
    BaseResponse getAll();
    BaseResponse getById(Integer id);
}
