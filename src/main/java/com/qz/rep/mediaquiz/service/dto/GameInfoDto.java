package com.qz.rep.mediaquiz.service.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
public class GameInfoDto {
    private final String id;
    private final Integer count;
}
