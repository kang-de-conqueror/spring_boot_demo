package com.example.demo.constant;

import org.springframework.context.MessageSource;

import java.util.HashMap;
import java.util.Locale;

import static java.util.Optional.ofNullable;

public class Status {
    public static final Integer ERROR = -1;
    public static final Integer SUCCESS = 0;
    public static final Integer ITEM_LIST_EMPTY = 1;
    public static final Integer ITEM_NOT_FOUND = 2;
    public static final Integer BAD_FORMAT_DATA = 200;
    public static final Integer ACCESS_IS_DENIED = 403;
    public static final Integer REQUEST_NOT_FOUND = 404;

    public static HashMap<Integer, String> StatusMap = new HashMap<>();

    static {
        StatusMap.put(ERROR, "error");
        StatusMap.put(SUCCESS, "success");
        StatusMap.put(ITEM_LIST_EMPTY, "error.item.list.empty");
        StatusMap.put(ITEM_NOT_FOUND, "error.item.not.found");
        StatusMap.put(BAD_FORMAT_DATA, "error.bad.format.data");
        StatusMap.put(ACCESS_IS_DENIED, "error.access.is.denied");
        StatusMap.put(REQUEST_NOT_FOUND, "error.request.not.found");
    }

    private static String getLocalDesc(int status) {
        return ofNullable(StatusMap.get(status)).orElse("error");
    }

    /**
     * message resource for error code with language
     *
     * @param status
     * @param language
     * @param messageSource
     * @return
     */
    public static String getMessage(int status, String language, MessageSource messageSource) {
        String messageCode = getLocalDesc(status);
        return messageSource.getMessage(messageCode, new Object[0], new Locale(ofNullable(language).orElse(Constant.LANGUAGE_VI)));
    }

    /**
     * message resource for error code with args
     *
     * @param status
     * @param language
     * @param messageSource
     * @param args
     * @return
     */
    public static String getMessage(int status, String language, MessageSource messageSource, Object[] args) {
        String messageCode = getLocalDesc(status);
        return messageSource.getMessage(messageCode, args, new Locale(ofNullable(language).orElse(Constant.LANGUAGE_VI)));
    }

    /**
     * for exception only - other case please use getMessage
     *
     * @param status
     * @return
     */
    public static String getDesc(int status) {
        String s = StatusMap.get(status);
        if (s == null) {
            return "There was an error during processing, so sorry for the inconvenience. Please try again later, thank you! Error code: " + status;
        }
        return s;
    }
}
