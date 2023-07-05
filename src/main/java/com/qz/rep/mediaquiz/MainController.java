package com.qz.rep.mediaquiz;

import com.qz.rep.mediaquiz.controller.dto.ResponseDto;
import org.springframework.http.ResponseEntity;

public class MainController {
    protected <T> ResponseEntity<ResponseDto<T>> ok(T body) {
        return ResponseEntity.ok(ResponseDto.ok(body));
    }

    protected  <T> ResponseEntity<ResponseDto<T>> badRequest(String description) {
        return ResponseEntity.badRequest().body(ResponseDto.badRequest(description));
    }
}
