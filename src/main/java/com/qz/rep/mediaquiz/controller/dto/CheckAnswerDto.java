package com.qz.rep.mediaquiz.controller.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class CheckAnswerDto {
    private Long questionId;
    private Boolean isCorrect;
    private String correctAnswer;
}
