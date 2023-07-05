package com.qz.rep.mediaquiz.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor
public class AnswerResultDto {
    private String question;
    private Boolean isCorrect;
}
