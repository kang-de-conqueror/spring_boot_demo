package com.example.demo.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

public interface HttpService {
    ResponseEntity<Object> get(String url);

    ResponseEntity<Object> post(String url, Object request, HttpHeaders httpHeaders);

    ResponseEntity<Object> put(String url, Object request, HttpHeaders httpHeaders);

    ResponseEntity<Object> post(String url, String urlEncodeRequest, HttpHeaders httpHeaders);

    ResponseEntity<Object> post(String url, Object request);

    ResponseEntity<Object> delete(String url, HttpHeaders httpHeaders);
}
