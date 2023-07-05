package com.qz.rep.mediaquiz.repository;

import com.qz.rep.mediaquiz.entity.Question;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {
    @Query(nativeQuery = true, value = "SELECT *  FROM question ORDER BY random() LIMIT :count")
    List<Question> findRandom(Integer count);
}
