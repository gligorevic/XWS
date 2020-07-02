package com.example.FeedbackService.repository;

import com.example.FeedbackService.domain.Grade;
import com.example.FeedbackService.dto.AverageGradeDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {

    Grade findByRequestId(Long id);

    @Query("SELECT new com.example.FeedbackService.dto.AverageGradeDTO(AVG(gr.grade), gr.agentUsername) FROM Grade gr WHERE gr.agentUsername IN ?1 GROUP BY gr.agentUsername")
    List<AverageGradeDTO> calculateAverageGrades(@Param("userEmails") List<String> userEmails);
}
