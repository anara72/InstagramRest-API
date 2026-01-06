package peaksoft.instogram.service;

import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.postDto.request.PostRequest;
import peaksoft.instogram.dto.postDto.response.PostResponse;

import java.util.List;

public interface PostService {
    PostResponse createPost(Long userId, PostRequest postRequest);
    PostResponse updatePost(Long postId, PostRequest postRequest);
    List<PostResponse> instFeed(Long userId);
    SimpleResponse deletePost(Long userId, Long postId);

}
