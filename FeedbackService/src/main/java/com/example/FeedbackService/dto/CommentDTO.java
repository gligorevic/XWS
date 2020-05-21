package com.example.FeedbackService.dto;

import com.example.FeedbackService.domain.CommentStatus;

public class CommentDTO {

    private String text;
    private String commentStatus;
    private Long requestId;
    private Long userId; // dao komentar

}
