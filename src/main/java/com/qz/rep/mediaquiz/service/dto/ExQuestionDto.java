package com.qz.rep.mediaquiz.service.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExQuestionDto {
    private Long id;
    private String question;
    private String answer;
    private Long value;
    private ExtCategoryDto category;
}
