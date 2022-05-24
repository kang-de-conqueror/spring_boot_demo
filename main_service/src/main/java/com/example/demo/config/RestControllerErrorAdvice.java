package com.example.demo.config;

import com.example.demo.constant.Constant;
import com.example.demo.constant.Status;
import com.example.demo.dto.response.BaseResponse;
import com.example.demo.exception.AbstractException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Locale;

/**
 * The type Rest controller error advice.
 */
@ControllerAdvice
@Slf4j
public class RestControllerErrorAdvice {
    private final MessageSource messageSource;
    private final ObjectMapper objectMapper;

    public RestControllerErrorAdvice(MessageSource messageSource, ObjectMapper objectMapper) {
        this.messageSource = messageSource;
        this.objectMapper = objectMapper;
    }

    @ExceptionHandler(value = AbstractException.class)
    private <T extends BaseResponse> ResponseEntity<T> handleException(HttpServletRequest request, AbstractException exception) throws JsonProcessingException {
        String language = getLanguage(request);
        String message = messageSource.getMessage(exception.getMessage(), exception.getMessageArgs(), new Locale(language));
        T response = exception.getResponseEntity(message);
        log.error("Exception response to client fail => {}", objectMapper.writeValueAsString(response));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(value = Throwable.class)
    private ResponseEntity<BaseResponse> handleOtherException(HttpServletRequest request, Throwable cause) {
        if (cause instanceof IOException && StringUtils.containsIgnoreCase(ExceptionUtils.getRootCauseMessage(cause), "Broken pipe")) {
            StringBuilder sb = new StringBuilder();
            Enumeration<String> iterator = request.getHeaderNames();
            while (iterator.hasMoreElements()) {
                String header = iterator.nextElement();
                sb.append(header).append("=").append(request.getHeader(header)).append("  ");
            }
            log.error("Broken pipe - End point: {} - Method: {} - Header: {}", request.getRequestURL(), request.getMethod(), sb);
            return null;
        }
        BaseResponse baseResponse;
        if (cause instanceof AccessDeniedException) {
            log.error("Exception response to client fail => {} ", cause.getMessage(), cause);
            baseResponse = new BaseResponse(Status.ACCESS_IS_DENIED, Status.getMessage(Status.ACCESS_IS_DENIED, getLanguage(request), messageSource));
            return new ResponseEntity<>(baseResponse, HttpStatus.FORBIDDEN);
        }
        baseResponse = new BaseResponse(Status.ERROR, Status.getMessage(Status.ERROR, getLanguage(request), messageSource));
        log.error("Exception response to client fail => {} ", cause.getMessage(), cause);

        return new ResponseEntity<>(baseResponse, HttpStatus.OK);
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    private ResponseEntity<BaseResponse> handleRequestMethodNotSupport(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        BaseResponse baseResponse = new BaseResponse(Status.REQUEST_NOT_FOUND, Status.getMessage(Status.REQUEST_NOT_FOUND, getLanguage(request), messageSource));
        log.error("Request method not supported => {}", ex.getMessage(), ex);

        return new ResponseEntity<>(baseResponse, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageConversionException.class, JsonProcessingException.class, UndeclaredThrowableException.class})
    public ResponseEntity<BaseResponse> handleValidationExceptions(HttpServletRequest request, Exception ex) {
        BaseResponse response = new BaseResponse(Status.BAD_FORMAT_DATA, Status.getMessage(Status.BAD_FORMAT_DATA, getLanguage(request), messageSource));
        log.error("Request annotation validation fail => {}", ex.getMessage(), ex);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private String getLanguage(HttpServletRequest request) {
        String langRequest = request.getParameter(Constant.LANGUAGE_PARAM);
        String langAttribute = (String) request.getAttribute(Constant.LANGUAGE_PARAM);
        return StringUtils.firstNonEmpty(langAttribute, langRequest, Constant.LANGUAGE_VI);
    }
}
