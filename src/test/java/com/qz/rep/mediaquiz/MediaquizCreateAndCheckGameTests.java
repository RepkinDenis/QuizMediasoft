package com.qz.rep.mediaquiz;

import com.qz.rep.mediaquiz.controller.dto.*;
import com.qz.rep.mediaquiz.entity.Question;
import com.qz.rep.mediaquiz.service.dto.AnswerResultDto;
import com.qz.rep.mediaquiz.service.dto.GameInfoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.cache.CacheManager;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //доступный порт
class MediaquizCreateAndCheckGameTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    private static String gameId;
    private static LinkedList<Question> questions;

    @Autowired
    private CacheManager cacheManager;


    @BeforeEach
    void createGame() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game",
                HttpMethod.POST,
                new HttpEntity<>(new CreateQuestionDto(5, 10, 210, null)),
                new ParameterizedTypeReference<ResponseDto<GameInfoDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok", dto.getCode());

        assertNotNull(dto.getResult());
        gameId = dto.getResult().getId();
        questions = (LinkedList<Question>) cacheManager.getCache("games").get(gameId, LinkedList.class);
    }

    @Test
    void getFirstQuestion() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game/" + gameId + "/1",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ResponseDto<QuestionDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok", dto.getCode());

        assertFalse(dto.getResult().getQuestion().isBlank()); //Возвращает true, если строка пустая или состоит только из пробельных символов
    }

    @Test
    void answerFirstQuestionOk() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game/" + gameId + "/1/check",
                HttpMethod.POST,
                new HttpEntity<>(new AnswerDto(null, "Ответ")),
                new ParameterizedTypeReference<ResponseDto<CheckAnswerDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok", dto.getCode());

        assertFalse(dto.getResult().getIsCorrect());
        assertFalse(dto.getResult().getCorrectAnswer() == "Ответ");
    }

    @Test
    void answerSecondQuestionOk() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game/" + gameId + "/1/check",
                HttpMethod.POST,
                new HttpEntity<>(new AnswerDto(null, "Ответ")),
                new ParameterizedTypeReference<ResponseDto<CheckAnswerDto>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok", dto.getCode());

        assertFalse(dto.getResult().getIsCorrect());
        assertFalse(dto.getResult().getCorrectAnswer() == "Ответ");
    }


    @Test
    void finishGame() {
        var dto = restTemplate.exchange(
                "http://localhost:" + port + "/game/" + gameId + "/finish",
                HttpMethod.POST,
                new HttpEntity<>(Collections.emptyMap()),
                new ParameterizedTypeReference<ResponseDto<List<AnswerResultDto>>>() {
                }).getBody();
        assertNotNull(dto);
        assertEquals("ok", dto.getCode());

        assertTrue(dto.getResult().size() == questions.size());
    }

}