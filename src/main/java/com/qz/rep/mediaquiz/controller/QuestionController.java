package com.qz.rep.mediaquiz.controller;


import com.qz.rep.mediaquiz.MainController;
import com.qz.rep.mediaquiz.controller.dto.AnswerDto;
import com.qz.rep.mediaquiz.controller.dto.CheckAnswerDto;
import com.qz.rep.mediaquiz.controller.dto.QuestionDto;
import com.qz.rep.mediaquiz.controller.dto.ResponseDto;
import com.qz.rep.mediaquiz.service.QuestionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("question")
public class QuestionController extends MainController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @GetMapping("random")
    public ResponseEntity<ResponseDto<QuestionDto>> getRandom() {
        var list = questionService.getRandom(1);
        if (list.isEmpty()) {
            return badRequest("Не найден ни один вопрос");
        }

        var question = list.get(0);
        return ok(QuestionDto.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .dif(question.getDifficulty())
                .category(question.getCategory())
                .build());
    }

    @PostMapping("check")
    public ResponseEntity<ResponseDto<CheckAnswerDto>> checkAnswer(@RequestBody AnswerDto answer) {
        if (answer.getQuestionId() == null) {
            return badRequest("Не заполнен id вопроса");
        }

        if (answer.getAnswer() == null) {
            return badRequest("Не заполнен ответ");
        }

        return questionService.findById(answer.getQuestionId())
                .map(question -> ok(CheckAnswerDto.builder()
                        .questionId(question.getId())
                        .correctAnswer(question.getAnswer())
                        .isCorrect(question.getAnswer().equals(answer.getAnswer()))
                        .build())
                )
                .orElse(badRequest("Не найден вопрос"));
    }
}
