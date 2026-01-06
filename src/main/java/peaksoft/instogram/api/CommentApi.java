package peaksoft.instogram.api;


import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.commentDto.request.CommentRequest;
import peaksoft.instogram.dto.commentDto.response.CommentResponse;
import peaksoft.instogram.service.CommentService;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentApi {
    private final CommentService commentService;

    //todo create comment
    @PostMapping("/{userId}/{postId}/add-comment")
    public CommentResponse  createComment(@PathVariable("userId") Long userId, @PathVariable("postId") Long postId, @RequestBody CommentRequest commentRequest) {
        return commentService.saveComment(userId, postId, commentRequest);
    }

    //todo find all comments by post-id
    @GetMapping("/{postId}/getComments")
    public List<CommentResponse> getComments(@PathVariable Long postId) {
        return commentService.findAllCommentsByPostId(postId);
    }

    //todo delete comment
    @DeleteMapping("/{commentId}/remove-comment")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse deleteCommentByiD(@PathVariable Long commentId) {
        return commentService.deleteCommentByiD(commentId);
    }
}
