package com.example.AgentApplication.controller;

import com.example.AgentApplication.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    private GradeService gradeService;
}
