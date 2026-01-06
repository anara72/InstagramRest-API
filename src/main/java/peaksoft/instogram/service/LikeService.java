package peaksoft.instogram.service;


import peaksoft.instogram.dto.SimpleResponse;

public interface LikeService {
    SimpleResponse likeForPost(Long userId, Long postId);
    SimpleResponse likeForComment(Long userId, Long commentId);
}