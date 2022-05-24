package com.example.demo.service.impl;

import com.example.demo.constant.ResultCode;
import com.example.demo.dto.AccountUser;
import com.example.demo.dto.BaseResponse;
import com.example.demo.model.BookEntity;
import com.example.demo.service.BookService;
import com.example.demo.service.HttpService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookServiceImpl implements BookService {

    // Service
    private final HttpService httpService;

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;

    private static final String API_BOOKS = "http://localhost:5001/main/api/v1/books";

    @Override
    public List<BookEntity> getAll() {
        BaseResponse baseResponse = objectMapper.convertValue(httpService.get(API_BOOKS).getBody(), BaseResponse.class);
        if (!ResultCode.SUCCESS.equals(baseResponse.getStatus())) {
            return new ArrayList<>();
        }

        return (List<BookEntity>) baseResponse.getData();
    }

    @Override
    public Optional<BookEntity> getById(String id) {
        BaseResponse baseResponse = objectMapper.convertValue(httpService.get(String.format("%s/%s", API_BOOKS, id)).getBody(), BaseResponse.class);
        if (!ResultCode.SUCCESS.equals(baseResponse.getStatus())) {
            return Optional.empty();
        }

        return Optional.of(objectMapper.convertValue(baseResponse.getData(), BookEntity.class));
    }

    @Override
    public BaseResponse create(BookEntity bookEntity) {
        AccountUser accountUser = (AccountUser) request.getSession().getAttribute("USER");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", accountUser.getToken()));

        BaseResponse baseResponse = objectMapper.convertValue(httpService.post(API_BOOKS, bookEntity, headers).getBody(), BaseResponse.class);

        return baseResponse;
    }

    @Override
    public BaseResponse update(String id, BookEntity bookEntity) {
        AccountUser accountUser = (AccountUser) request.getSession().getAttribute("USER");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", accountUser.getToken()));

        BaseResponse baseResponse = objectMapper.convertValue(httpService.put(String.format("%s/%s", API_BOOKS, id), bookEntity, headers).getBody(), BaseResponse.class);

        return baseResponse;
    }

    @Override
    public BaseResponse delete(String id) {
        AccountUser accountUser = (AccountUser) request.getSession().getAttribute("USER");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", String.format("Bearer %s", accountUser.getToken()));

        BaseResponse baseResponse = objectMapper.convertValue(httpService.delete(String.format("%s/%s", API_BOOKS, id), headers).getBody(), BaseResponse.class);

        return baseResponse;
    }
}
