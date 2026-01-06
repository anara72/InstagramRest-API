package peaksoft.instogram.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.userDto.request.UserRequest;
import peaksoft.instogram.dto.userDto.request.UserUpdateRequest;
import peaksoft.instogram.dto.userDto.response.UserProfileResponse;
import peaksoft.instogram.dto.userDto.response.UserResponse;
import peaksoft.instogram.dto.userDto.response.UserResponseById;
import peaksoft.instogram.dto.userDto.response.UserUpdateResponse;
import peaksoft.instogram.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    //todo get user by id
    @GetMapping("/{id}")
    public UserResponseById getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    //todo get all users
    @GetMapping("/all-users")
    public List<UserResponse> getAllUsers()
    { return userService.getAllUsers(); }

    //todo update user by id
    @PutMapping("/{id}/update")
    public SimpleResponse updateUser(@PathVariable Long id, @RequestBody UserUpdateRequest userUpdateRequest){
        return userService.updateUser(id, userUpdateRequest);
    }

    //todo delete user by id
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public SimpleResponse deleteUser(@PathVariable Long id){
        return userService.deleteUserById(id);
    }

    //todo get user's Profile
    @GetMapping("/{id}/user-profile")
    public UserProfileResponse getProfile(@PathVariable Long id){
        return userService.userProfile(id);
    }
}

