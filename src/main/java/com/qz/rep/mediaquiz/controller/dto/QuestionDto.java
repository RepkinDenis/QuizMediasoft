package com.qz.rep.mediaquiz.controller.dto;

import com.qz.rep.mediaquiz.entity.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;

@Getter
@ToString
@Builder
public class QuestionDto {
    private final Long id;
    private final String question;

    @Min(1)
    @Max(10000)
    private final Integer dif;

    private final Category category;
}
