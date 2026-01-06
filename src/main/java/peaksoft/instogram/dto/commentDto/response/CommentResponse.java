package peaksoft.instogram.dto.commentDto.response;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CommentResponse(String comment, String username, String postTitle,
        int likeCount, LocalDate createdAt) {
}
