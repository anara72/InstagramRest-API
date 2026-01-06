package peaksoft.instogram.service;

    import peaksoft.instogram.dto.SimpleResponse;
    import peaksoft.instogram.dto.commentDto.request.CommentRequest;
    import peaksoft.instogram.dto.commentDto.response.CommentResponse;

    import java.util.List;

    public interface CommentService {
        CommentResponse saveComment(Long userId, Long postId, CommentRequest commentRequest);
        List<CommentResponse> findAllCommentsByPostId(Long postId);
        SimpleResponse deleteCommentByiD(Long commentId);
    }


