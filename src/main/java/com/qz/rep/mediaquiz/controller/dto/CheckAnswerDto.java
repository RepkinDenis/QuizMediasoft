package com.qz.rep.mediaquiz.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class CheckAnswerDto {
    private final Long questionId;
    private final Boolean isCorrect;
    private final String correctAnswer;
}
