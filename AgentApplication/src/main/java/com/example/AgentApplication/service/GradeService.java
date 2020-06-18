package com.example.AgentApplication.service;

import com.example.AgentApplication.repository.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {

    @Autowired
    private GradeRepository gradeRepository;
}
