package com.example.demo.service.impl;

import com.example.demo.service.HttpService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HttpServiceImpl implements HttpService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public ResponseEntity<Object> get(String url) {
        try {
            log.info("START get() - url = {}", url);
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.GET, null, Object.class);
            log.info("END get() - url = {}, response body {}",
                    url, objectMapper.writeValueAsString(result));
            return result;
        } catch (RestClientResponseException re) {
            log.error("Call {} server catch RestClientResponseException exception: {}", url, re.getMessage());
        } catch (JsonProcessingException je) {
            log.error("Convert value as String failed at - get() - url = {}, exception {}", url, je.getMessage());
        }

        return null;
    }

    @Override
    public ResponseEntity<Object> post(String url, Object request, HttpHeaders headers) {
        try {
            log.info("START post() - url = {}, header: {}, request body: {}",
                    url, objectMapper.writeValueAsString(headers), objectMapper.writeValueAsString(request));
            HttpEntity<Object> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
            log.info("END post() - url = {}, header: {}, response body {}",
                    url, objectMapper.writeValueAsString(headers), objectMapper.writeValueAsString(result));
            return result;
        } catch (RestClientResponseException re) {
            log.error("Call {} server catch RestClientResponseException exception: {}", url, re.getMessage());
        } catch (JsonProcessingException je) {
            log.error("Convert value as String failed at - post() - url = {}, exception {}", url, je.getMessage());
        }

        return null;
    }

    @Override
    public ResponseEntity<Object> put(String url, Object request, HttpHeaders headers) {
        try {
            log.info("START put() - url = {}, header: {}, request body: {}",
                    url, objectMapper.writeValueAsString(headers), objectMapper.writeValueAsString(request));
            HttpEntity<Object> entity = new HttpEntity<>(request, headers);
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.PUT, entity, Object.class);
            log.info("END put() - url = {}, header: {}, response body {}",
                    url, objectMapper.writeValueAsString(headers), objectMapper.writeValueAsString(result));
            return result;
        } catch (RestClientResponseException re) {
            log.error("Call {} server catch RestClientResponseException exception: {}", url, re.getMessage());
        } catch (JsonProcessingException je) {
            log.error("Convert value as String failed at - put() - url = {}, exception {}", url, je.getMessage());
        }

        return null;
    }

    @Override
    public ResponseEntity<Object> post(String url, String urlEncodeRequest, HttpHeaders headers) {
        try {
            log.info("START post() - url = {}, header: {}, request body {}",
                    url, objectMapper.writeValueAsString(headers), urlEncodeRequest);
            HttpEntity<String> entity = new HttpEntity<>(urlEncodeRequest, headers);
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
            log.info("END post() - url = {}, header: {}, response body {}",
                    url, objectMapper.writeValueAsString(headers), objectMapper.writeValueAsString(result));
            return result;
        } catch (RestClientResponseException re) {
            log.error("Call {} server catch RestClientResponseException exception: {}", url, re.getMessage());
        } catch (JsonProcessingException je) {
            log.error("Convert value as String failed at - post() - url = {}, exception {}", url, je.getMessage());
        }

        return null;
    }

    @Override
    public ResponseEntity<Object> post(String url, Object request) {
        try {
            log.info("START post() - url = {}, request body {}", url, objectMapper.writeValueAsString(request));
            HttpEntity<Object> entity = new HttpEntity<>(request);
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.POST, entity, Object.class);
            log.info("END post() - url = {}, response body {}", url, objectMapper.writeValueAsString(result));
            return result;
        } catch (RestClientResponseException re) {
            log.error("Call {} server catch RestClientResponseException exception: {}", url, re.getMessage());
        } catch (JsonProcessingException je) {
            log.error("Convert value as String failed at - post() - url = {}, exception {}", url, je.getMessage());
        }
        return null;
    }

    @Override
    public ResponseEntity<Object> delete(String url, HttpHeaders headers) {
        try {
            log.info("START delete() - url = {}, header: {}",
                    url, objectMapper.writeValueAsString(headers));
            HttpEntity<Object> entity = new HttpEntity<>(headers);
            ResponseEntity<Object> result = restTemplate.exchange(url, HttpMethod.DELETE, entity, Object.class);
            log.info("END delete() - url = {}, header: {}, response body {}",
                    url, objectMapper.writeValueAsString(headers), objectMapper.writeValueAsString(result));
            return result;
        } catch (RestClientResponseException re) {
            log.error("Call {} server catch RestClientResponseException exception: {}", url, re.getMessage());
        } catch (JsonProcessingException je) {
            log.error("Convert value as String failed at - delete() - url = {}, exception {}", url, je.getMessage());
        }

        return null;
    }
}
