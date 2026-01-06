package peaksoft.instogram.dto.commentDto.request;

import lombok.Builder;

import java.time.LocalDate;

public record CommentRequest(String comment) {
    @Builder

    public static record CommentResponse(String comment, String username, String postTitle, int likeCount, LocalDate createdAt) {

    }
}
