package com.example.FeedbackService.dto;

import com.example.FeedbackService.domain.CommentStatus;

public class CommentStatusDTO {
    private CommentStatus commentStatus;

    public CommentStatusDTO() {}

    public CommentStatusDTO(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }

    public CommentStatus getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(CommentStatus commentStatus) {
        this.commentStatus = commentStatus;
    }
}
