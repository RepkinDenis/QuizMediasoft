package com.qz.rep.mediaquiz.controller;

import com.qz.rep.mediaquiz.MainController;
import com.qz.rep.mediaquiz.controller.dto.*;
import com.qz.rep.mediaquiz.service.GameService;
import com.qz.rep.mediaquiz.service.dto.AnswerResultDto;
import com.qz.rep.mediaquiz.service.dto.GameInfoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;
@RestController
@RequestMapping("game")
public class GameController extends MainController {

    /*@GetMapping("/")
    public ResponseEntity createTodo() {
        try {
            return ResponseEntity.ok("ok");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Произошла ошибка");
        }
    }*/
    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto<GameInfoDto>> createGame(@RequestBody CreateQuestionDto dto) {
        if (dto.getCount() == null) {
            return badRequest("Не заполнено количество игры");
        }

        if (dto.getMinDifficulty() == null) {
            return badRequest("Не заполнена минимальная сложность игры");
        }

        if (dto.getMaxDifficulty() == null) {
            return badRequest("Не заполнена максимальная сложность игры");
        }

        if (dto.getCount() > 100) {
            dto.setCount(100);
        }
        return ok(gameService.createGame(dto));
    }

    @GetMapping("{gameId}/{number}")
    public ResponseEntity<ResponseDto<QuestionDto>> getQuestion(@PathVariable String gameId, @PathVariable Integer number) {
        return gameService.getQuestion(gameId, number)
                .map(question -> ok(QuestionDto.builder()
                        .id(question.getId())
                        .question(question.getQuestion())
                        .dif(question.getDifficulty())
                        .category(question.getCategory())
                        .build())
                ).orElse(badRequest("Не найден вопрос"));
    }

    @PostMapping("{gameId}/{number}/check")
    public ResponseEntity<ResponseDto<CheckAnswerDto>> checkAnswer(@PathVariable String gameId, @PathVariable Integer number, @RequestBody AnswerDto answer) {
        return gameService.checkAnswer(gameId, number, answer.getAnswer())
                .map(this::ok)
                .orElse(badRequest("Не найден вопрос"));
    }

    @PostMapping("{gameId}/finish")
    public ResponseEntity<ResponseDto<List<AnswerResultDto>>> finish(@PathVariable String gameId) {
        var list = gameService.finish(gameId);
        if (list.isEmpty()) {
            return badRequest("Не найдена игра");
        }

        return ok(list);
    }
}
