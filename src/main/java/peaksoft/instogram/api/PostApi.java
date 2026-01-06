package peaksoft.instogram.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.postDto.request.PostRequest;
import peaksoft.instogram.dto.postDto.response.PostResponse;
import peaksoft.instogram.service.PostService;
import peaksoft.instogram.dto.postDto.response.PostResponse;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostApi {
    private final PostService postService;

    //todo create post
    @PostMapping("/{userId}/save-post")
    public PostResponse createPost(@PathVariable Long userId, @RequestBody PostRequest postRequest) {
        return postService.createPost(userId, postRequest);
    }

    //todo update post
    @PutMapping("/{postId}/edit-post")
    public PostResponse updatePost(@PathVariable Long postId, @RequestBody PostRequest postRequest) {
        return postService.updatePost(postId, postRequest);
    }

    //todo get descending feed
    @GetMapping("/{userId}/feeds")
    public List<PostResponse> getPosts(@PathVariable Long userId) {
        return postService.instFeed(userId);
    }

    //todo delete post
    @DeleteMapping("/{userId}/remove-post")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public SimpleResponse removePost(@PathVariable Long userId, @RequestParam Long postId) {
        return postService.deletePost(userId, postId);
    }
}
