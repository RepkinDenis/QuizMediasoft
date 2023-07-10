package com.qz.rep.mediaquiz.controller.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class AnswerDto {
    private final Long questionId;
    private final String answer;
}
