package com.qz.rep.mediaquiz.service.dto;


import lombok.*;

@Getter
@ToString
@AllArgsConstructor
public class AnswerResultDto {
    private final String question;
    private final Boolean isCorrect;
}
