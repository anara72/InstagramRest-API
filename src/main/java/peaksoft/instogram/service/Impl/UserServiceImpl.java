package peaksoft.instogram.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.postDto.response.PostResponse;
import peaksoft.instogram.dto.userDto.request.UserRequest;
import peaksoft.instogram.dto.userDto.request.UserUpdateRequest;
import peaksoft.instogram.dto.userDto.response.UserProfileResponse;
import peaksoft.instogram.dto.userDto.response.UserResponse;
import peaksoft.instogram.dto.userDto.response.UserResponseById;
import peaksoft.instogram.dto.userDto.response.UserUpdateResponse;
import peaksoft.instogram.entity.Image;
import peaksoft.instogram.entity.User;
import peaksoft.instogram.repository.UserRepository;
import peaksoft.instogram.service.UserService;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    @Override
    public SimpleResponse saveUser(UserRequest userRequest) {
        User user = new User();
        user.setUserName(user.getUsername());
        user.setEmail(user.getEmail());
        user.setPassword(user.getPassword());
        userRepo.save(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User saved successfully")
                .build();
    }
      @Override
    public UserResponseById getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User with id " + id + " is not found"));

        return UserResponseById.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    @Override
    public List<UserResponse> getAllUsers() {
        return userRepo.getAllUsers();
    }

    @Override
    public SimpleResponse updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        user.setUserName(user.getUsername());
        user.setPassword(user.getPassword());
        user.setEmail(user.getEmail());

        userRepo.save(user);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User updated successfully")
                .build();
    }

    @Override
    public SimpleResponse deleteUserById(Long id) {
        userRepo.deleteById(id);

        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("User deleted successfully")
                .build();
    }

    @Override
    public UserProfileResponse userProfile(Long id) {
        User user = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User with such + " + id + " not found"));
        int countSubscriptions = user.getFollowerList() == null ? 0 : user.getFollowerList().getSubscriptions().size();
        int countSubscribers = user.getFollowerList() == null ? 0 : user.getFollowerList().getSubscriptions().size();

        List<PostResponse> postList = user.getPosts().stream().map(p -> PostResponse.builder().id(p.getId()).title(p.getTitle())
                        .description(p.getDescription()).imagUrl(String.valueOf(p.getImages().stream().map(Image::getImagUrl).toList()))
                        .collabUsers(p.getCollabs().stream().map(User::getUsername).toList()).createdAt(p.getCreatedAt()).build())
                .sorted(Comparator.comparing(PostResponse::createdAt).reversed()).toList();

        String fullName = user.getUserInfo() != null && user.getUserInfo().getFullName() != null ? user.getUserInfo().getFullName() : "";
        //String fullName = user.getUserInfo().getFullName() != null ? user.getUserInfo().getFullName() : "";
        String imageUrl = user.getUserInfo() != null && user.getUserInfo().getImage() != null ? user.getUserInfo().getImage() : null;
        //String imageUrl = user.getUserInfo().getImage() != null ? user.getUserInfo().getImage() : null;
        return UserProfileResponse.builder().username(user.getUsername()).fullName(fullName)
                .image(imageUrl).subscribers(countSubscribers).subscriptions(countSubscriptions).posts(postList).build();
    }
}