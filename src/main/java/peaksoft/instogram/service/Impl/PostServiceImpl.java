package peaksoft.instogram.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.instogram.config.jwt.JwtService;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.postDto.request.PostRequest;
import peaksoft.instogram.dto.postDto.response.PostResponse;
import peaksoft.instogram.entity.*;
import peaksoft.instogram.enums.Role;
import peaksoft.instogram.repository.LikeRepository;
import peaksoft.instogram.repository.PostRepository;
import peaksoft.instogram.repository.UserRepository;
import peaksoft.instogram.service.PostService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public PostResponse createPost(Long userId, PostRequest postRequest) {
        User currentUser = jwtService.checkToken();
        if(currentUser.getId() != userId){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't create posts for this user");
            //throw new AccessDeniedException("You are not allowed to create post!");
        }
        Post post = new Post();
        if(postRequest.imagUrl() == null || postRequest.imagUrl().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the post must have image to create pos");
            //throw new BadRequestException("the post must have image to create post");
        }
        post.setTitle(postRequest.title());
        post.setDescription(postRequest.description());
        post.setCreatedAt(LocalDate.now());
        post.setUser(currentUser);
        //Бир постко бир канча User лерди отметка кыла алсын:
        if(postRequest.collabsUserId() != null && !postRequest.collabsUserId().isEmpty()){ //проверка отмечает ли людей так [x, y] в своем посте
            List<User> userCollabs = userRepository.findAllById(postRequest.collabsUserId());
            post.setCollabs(userCollabs);
        }
        Image image = new Image();
        image.setImagUrl(postRequest.imagUrl());
        image.setPost(post);
        post.getImages().add(image);
        postRepository.save(post);
        Like like = new Like();
        like.setLiked(false);
        like.setPost(post);
        like.setUser(currentUser);
        likeRepository.save(like);
        return PostResponse.builder().id(post.getId()).title(post.getTitle()).description(post.getDescription())
                .username(currentUser.getUsername()).collabUsers(post.getCollabs().stream().map(User::getUsername).toList()).imagUrl(image.getImagUrl())
                .createdAt(post.getCreatedAt()).build();
    }

    @Override
    public PostResponse updatePost(Long postId, PostRequest postRequest) {
        User currentUser = jwtService.checkToken();
        //post update болуп жатканда title and description эле озгорсун
        Post post = postRepository.findById(postId).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "post not found"));
        if(!post.getUser().getId().equals(currentUser.getId())){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't update posts for this user");
            //throw new AccessIsDeniedException("You are not allowed to update post!");
        }
        post.setTitle(postRequest.title());
        post.setDescription(postRequest.description());
        postRepository.save(post);
        //Image image = post.getImages().isEmpty() ? null : post.getImages().get(0); //если пуста - null, если не пуста - берется первая картина(post.getImages().get(0)) со списка
        return PostResponse.builder().id(post.getId()).title(post.getTitle()).description(post.getDescription())
                .username(post.getUser().getUsername()).collabUsers(post.getCollabs().stream().map(User::getUsername).toList()).imagUrl(post.getImages().get(0).getImagUrl()) //.get(0).getImageUrl - берется та эе картинка с списка
                .createdAt(post.getCreatedAt()).build();
    }

    @Override
    public List<PostResponse> instFeed(Long userId) {
        //User-дин лентасыдан акыркы кирген пост биринчи чыгыш керек.
        // Подпискасында бар user-лердин жана озунун посту.
        User user = userRepository.findById(userId).orElseThrow(()->
                new RuntimeException("User not found"));
        List<Long> subscriptionsId = new ArrayList<>();
        //проверяем есть ли followers
//        if(user.getFollowers() != null){
//            subscriptionsId.addAll(user.getFollowerList().getSubscriptions().stream().map(User::getId).toList());
//        }
        subscriptionsId.add(user.getId()); //жана озунун посту
        if(subscriptionsId.isEmpty()){ return Collections.emptyList(); }

        List<Post> postList = postRepository.findAllByIdLikeDescending(subscriptionsId);
        return postList.stream().map(p -> PostResponse.builder().id(p.getId()).title(p.getTitle()).description(p.getDescription()).username(p.getUser().getUsername())
                .imagUrl(p.getImages().stream().findFirst().map(Image::getImagUrl).orElse(null)).collabUsers(p.getCollabs().stream().map(User::getUsername).toList())
                .createdAt(p.getCreatedAt()).build()).toList();
    }

    @Override
    public SimpleResponse deletePost(Long userId, Long postId) {
        User currentUser = jwtService.checkToken();
        // User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new RuntimeException("Post not found"));
        if(!post.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete posts for this user");
            //throw new AccessIsDeniedException("You are not allowed to delete post!");
        }
//        if(post.getUser().getPosts() == null){
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"there is no post");
//        }
//        if(post.getLikes() != null && !post.getLikes().isEmpty()) { //удаляем вручную, так как в классе не указан orphanRemoval, а иначе 500
//            likeRepository.deleteAll(post.getLikes());
//        }
        post.getUser().getPosts().removeIf(p -> p.getId().equals(postId));
        postRepository.delete(post);
        //userRepository.save(user); we don't need this part 'cause Hibernate will update User itself using @Transactional
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("post successfully deleted").build();
    }
}

