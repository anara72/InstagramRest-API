package peaksoft.instogram.service;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.userDto.request.UserRequest;
import peaksoft.instogram.dto.userDto.response.UserProfileResponse;
import peaksoft.instogram.dto.userDto.request.UserUpdateRequest;
import peaksoft.instogram.dto.userDto.response.UserResponse;
import peaksoft.instogram.dto.userDto.response.UserResponseById;
import peaksoft.instogram.dto.userDto.response.UserUpdateResponse;

import java.util.List;

public interface UserService {
    SimpleResponse saveUser(UserRequest userRequest);
    UserResponseById getUserById(Long id);
    List<UserResponse> getAllUsers();
    SimpleResponse updateUser(Long id, UserUpdateRequest userUpdateRequest);
    SimpleResponse deleteUserById(Long id);
    UserProfileResponse userProfile(Long id);
}







