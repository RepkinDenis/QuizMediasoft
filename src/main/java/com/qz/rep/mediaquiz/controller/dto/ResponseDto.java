package com.qz.rep.mediaquiz.controller.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class ResponseDto<T> {
    private final String code;
    private final String description;
    private final T result;

    public static <T> ResponseDto<T> ok(T result) {

        return new ResponseDto<>("ok", null, result);
    }

    public static <T> ResponseDto<T> badRequest(String description) {
        return new ResponseDto<>("bad_request", description, null);
    }
}
