package peaksoft.instogram.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.instogram.config.jwt.JwtService;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.commentDto.request.CommentRequest;
import peaksoft.instogram.dto.commentDto.response.CommentResponse;
import peaksoft.instogram.entity.Comment;
import peaksoft.instogram.entity.Like;
import peaksoft.instogram.entity.Post;
import peaksoft.instogram.entity.User;
import peaksoft.instogram.enums.Role;
import peaksoft.instogram.repository.CommentRepository;
import peaksoft.instogram.repository.LikeRepository;
import peaksoft.instogram.repository.PostRepository;
import peaksoft.instogram.repository.UserRepository;
import peaksoft.instogram.service.CommentService;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
    @Transactional
    @RequiredArgsConstructor
    public class CommentServiceImpl implements CommentService {
        private final CommentRepository commentRepository;
        private final PostRepository postRepository;
        private final UserRepository userRepository;
        private final LikeRepository likeRepository;
        private final JwtService jwtService;

        @Override
        public CommentResponse saveComment(Long userId, Long postId, CommentRequest commentRequest) {
            User currentUser = jwtService.checkToken();
            if(!currentUser.getId().equals(userId)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You can't to comment for this user");
                //throw new BadRequestException("You can't to comment for this user");
            }
            //User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
            Comment comment = new Comment();
            comment.setComment(commentRequest.comment());
            comment.setCreatedAt(LocalDate.now());
            comment.setUser(currentUser);
            comment.setPost(post);
            commentRepository.save(comment);
            //лайк кошо тузулсун. Бирок лайктын саны 0 болуп турсун.
            Like like = new Like();
            like.setUser(currentUser);
            like.setPost(post);
            like.setComment(comment);
            like.setLiked(false);
            comment.getLikes().add(like); //add LIKE to collection of comment
            likeRepository.save(like);
            long countLike = comment.getLikes().stream().filter(Like::isLiked).count();
            return CommentResponse.builder().comment(comment.getComment()).username(comment.getUser().getUsername()).postTitle(comment.getPost().getTitle())
                    .likeCount((int)countLike).createdAt(comment.getCreatedAt()).build();
        }

        @Override
        public List<CommentResponse> findAllCommentsByPostId(Long postId) {
            Post post = postRepository.findById(postId).orElseThrow(() -> new NoSuchElementException("Post not found"));
            List<Comment> comments = commentRepository.findAllByPostId(postId);
            if(comments.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "comment is not found");
                //throw new ChangeSetPersister.NotFoundException("comment is not found");
            }
            //long countLike = likeRepository.findAll().stream().filter(Like::isLike).count();
            List<CommentResponse> list = comments.stream().map(c -> {
                long countLike = likeRepository.findAll().stream().filter(Like::isLiked).count();
                return CommentResponse.builder().comment(c.getComment())
                        .username(c.getUser().getUsername()).postTitle(c.getPost().getTitle()).likeCount((int) countLike).createdAt(c.getCreatedAt()).build();
            }).toList();
            return list;
        }

        @Override
        public SimpleResponse deleteCommentByiD(Long commentId) {
            User currentUser = jwtService.checkToken();
            Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new NoSuchElementException("Comment not found"));
            if(!comment.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You are not allowed to delete this comment");
                //throw new AccessIsDeniedException("You are not allowed to delete this comment");
            }
            commentRepository.deleteById(commentId);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("comment is removed").build();
        }
    }

