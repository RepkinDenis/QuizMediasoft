package com.qz.rep.mediaquiz;

import com.qz.rep.mediaquiz.controller.dto.*;
import com.qz.rep.mediaquiz.entity.Question;
import com.qz.rep.mediaquiz.service.dto.AnswerResultDto;
import com.qz.rep.mediaquiz.service.dto.GameInfoDto;
import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//доступный порт
class MediaquizGameTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String gameId;
    private static LinkedList<Question> questions;

    @Autowired
    private CacheManager cacheManager;


    @Test
    void createGameWithoutCount() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game",
                HttpMethod.POST,
                new HttpEntity<>(new CreateQuestionDto()),
                new ParameterizedTypeReference<ResponseDto<GameInfoDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("bad_request", dto.getCode());

        assertEquals("Не заполнено количество игры", dto.getDescription());
        var result = dto.getResult();
        assert result == null;
    }

    @Test
    void createGameWithoutMinDifficult() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game",
                HttpMethod.POST,
                new HttpEntity<>(new CreateQuestionDto(5, null, null, null)),
                new ParameterizedTypeReference<ResponseDto<GameInfoDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("bad_request", dto.getCode());

        assertEquals("Не заполнена минимальная сложность игры", dto.getDescription());
        var result = dto.getResult();
        assert result == null;
    }


    @Test
    void createGameWithoutMaxDifficult() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game",
                HttpMethod.POST,
                new HttpEntity<>(new CreateQuestionDto(5, 10, null, null)),
                new ParameterizedTypeReference<ResponseDto<GameInfoDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("bad_request", dto.getCode());

        assertEquals("Не заполнена максимальная сложность игры", dto.getDescription());
        var result = dto.getResult();
        assert result == null;
    }

    @Test
    void createGameWithoutCategories_ShouldShowOk() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game",
                HttpMethod.POST,
                new HttpEntity<>(new CreateQuestionDto(5, 10, 210, null)),
                new ParameterizedTypeReference<ResponseDto<GameInfoDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok",dto.getCode());

        assertNotNull(dto.getResult());
    }

    @Test
    void createGame_ShouldShowOk() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game",
                HttpMethod.POST,
                new HttpEntity<>(new CreateQuestionDto(5, 10, 210, List.of(2L))), //List.of(2L) - список из одного элемента (числа 2 типа Long)
                new ParameterizedTypeReference<ResponseDto<GameInfoDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok", dto.getCode());

        assertNotNull(dto.getResult());
    }

    @Test
    void createGame_ShouldShowCount() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game",
                HttpMethod.POST,
                new HttpEntity<>(new CreateQuestionDto(5, 10, 210, null)),
                new ParameterizedTypeReference<ResponseDto<GameInfoDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok", dto.getCode());

        assertNotNull(dto.getResult());

        assertTrue(dto.getResult().getCount() == 5);
    }
}