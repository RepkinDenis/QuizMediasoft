package com.qz.rep.mediaquiz.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class ExQuestionDto {
    private final Long id;
    private final String question;
    private final String answer;
    private final Long value;
    private final ExtCategoryDto category;
}
