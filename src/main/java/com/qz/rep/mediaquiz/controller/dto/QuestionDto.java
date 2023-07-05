package com.qz.rep.mediaquiz.controller.dto;

import com.qz.rep.mediaquiz.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class QuestionDto {
    private Long id;
    private String question;
    private Integer dif;
    private Category category;
}
