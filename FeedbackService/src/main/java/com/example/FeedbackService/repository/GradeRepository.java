package com.example.FeedbackService.repository;

import com.example.FeedbackService.domain.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GradeRepository extends JpaRepository<Grade,Long> {

        Grade findByRequestId(Long id);

}
