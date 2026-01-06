package peaksoft.instogram.service.Impl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.instogram.config.jwt.JwtService;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.entity.Comment;
import peaksoft.instogram.entity.Like;
import peaksoft.instogram.entity.Post;
import peaksoft.instogram.entity.User;
import peaksoft.instogram.repository.CommentRepository;
import peaksoft.instogram.repository.PostRepository;
import peaksoft.instogram.repository.UserRepository;
import peaksoft.instogram.service.LikeService;
import peaksoft.instogram.repository.LikeRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LikeServiceImpl implements LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CommentRepository commentRepository;

    @Override
    public SimpleResponse likeForPost(Long userId, Long postId) {
        User currentUser = jwtService.checkToken();
        if(!currentUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to like this post");
        }
        //User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Post post = postRepository.findById(postId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found"));
        List<Like> likeList = likeRepository.findByUser_IdAndPost_Id(userId, postId); //search Like of this Post
        if(!likeList.isEmpty()){
            likeRepository.deleteAll(likeList);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Like is removed").build();
        }
        Like like = new Like();
        like.setUser(currentUser);
        like.setPost(post);
        like.setLiked(true);
        likeRepository.save(like);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Like is added").build();
    }

    @Override
    public SimpleResponse likeForComment(Long userId, Long commentId) {
        User currentUser = jwtService.checkToken();
        if(!currentUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to like this comment");
        }
        //User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found"));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "comment not found"));
        List<Like> likeList = likeRepository.findByUser_IdAndComment_Id(userId, commentId); //search Like of this Comment
        if(!likeList.isEmpty()){
            likeRepository.deleteAll(likeList);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Like is removed").build();
        }
        Like like = new Like();
        like.setUser(currentUser);
        like.setComment(comment);
        like.setLiked(true);
        likeRepository.save(like);
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Like is added").build();
    }
}