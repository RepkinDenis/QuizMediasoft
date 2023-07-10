package com.qz.rep.mediaquiz.controller.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import java.util.List;

@Getter
@ToString
@AllArgsConstructor
public class CreateQuestionDto {
    private final Integer count;

    @Min(1)
    private final Integer minDifficulty;

    @Max(10000)
    private final Integer maxDifficulty;

    private final List<Long> categories;
}
