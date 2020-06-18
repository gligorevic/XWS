package com.example.FeedbackService.dto;

import com.example.FeedbackService.domain.CommentStatus;

public class CommentDTO {

    private String text;
    private CommentStatus commentStatus;
    private Long requestId;
    private String username; // dao komentar

}
