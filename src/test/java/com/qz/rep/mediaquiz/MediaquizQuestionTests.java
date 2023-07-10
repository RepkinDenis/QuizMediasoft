package com.qz.rep.mediaquiz;

import com.qz.rep.mediaquiz.controller.dto.AnswerDto;
import com.qz.rep.mediaquiz.controller.dto.CheckAnswerDto;
import com.qz.rep.mediaquiz.controller.dto.QuestionDto;
import com.qz.rep.mediaquiz.controller.dto.ResponseDto;
import  org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //доступный порт
class MediaquizQuestionTests {

	@LocalServerPort
	private int port;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void ShowRandomQuestion() {
		var dto = restTemplate.exchange(
				"http://localhost:" + port + "/question/random",
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<ResponseDto<QuestionDto>>() {
				}).getBody();
		assertNotNull(dto);
		assertEquals("ok",dto.getCode());

		var result = dto.getResult();
		assertNotNull(result.getQuestion());
		assertNotNull(result.getCategory());
		assertNotNull(result.getDif());
	}

	@Test
	void checkCorrectAnswer() {
		var dto = restTemplate.exchange(
				"http://localhost:" + port + "/question/check",
				HttpMethod.POST,
				new HttpEntity<>(new AnswerDto(2L, "центнер")),
				new ParameterizedTypeReference<ResponseDto<CheckAnswerDto>>() {
				}).getBody();

		assertNotNull(dto);
		assertEquals("ok",dto.getCode());

		var result = dto.getResult();
		assertTrue(result.getIsCorrect());
		assertTrue(result.getQuestionId() == 2L);
		assertEquals("центнер",result.getCorrectAnswer());
	}

	@Test
	void checkIncorrectAnswer() {
		var dto = restTemplate.exchange(
				"http://localhost:" + port + "/question/check",
				HttpMethod.POST,
				new HttpEntity<>(new AnswerDto(2L, "тонна")),
				new ParameterizedTypeReference<ResponseDto<CheckAnswerDto>>() {
				}).getBody();

		assertNotNull(dto);
		assertEquals("ok",dto.getCode());

		var result = dto.getResult();
		assertFalse(result.getIsCorrect());
		assertTrue(result.getQuestionId() == 2L);
		assertEquals("центнер", result.getCorrectAnswer());
	}


	@Test
	void IncorrectIdRequest() {
		var dto = restTemplate.exchange(
				"http://localhost:" + port + "/question/check",
				HttpMethod.POST,
				new HttpEntity<>(new AnswerDto()),
				new ParameterizedTypeReference<ResponseDto<CheckAnswerDto>>() {
				}).getBody();

		assertNotNull(dto);
		assertEquals("bad_request",dto.getCode());

		assertEquals("Не заполнен id вопроса", dto.getDescription());
		assertNull(dto.getResult());
	}

	@Test
	void IncorrectAnswerRequest() {
		var dto = restTemplate.exchange(
				"http://localhost:" + port + "/question/check",
				HttpMethod.POST,
				new HttpEntity<>(new AnswerDto(2L, null)),
				new ParameterizedTypeReference<ResponseDto<CheckAnswerDto>>() {
				}).getBody();

		assertNotNull(dto);
		assertEquals("bad_request",dto.getCode());

		assertEquals("Не заполнен ответ",dto.getDescription());
		assertNull(dto.getResult());
	}
}
