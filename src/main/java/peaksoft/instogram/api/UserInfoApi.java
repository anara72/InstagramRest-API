package peaksoft.instogram.api;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.userInfoDto.request.InfoUpdateRequest;
import peaksoft.instogram.dto.userInfoDto.request.UserInfoRequest;
import peaksoft.instogram.dto.userInfoDto.response.UserInfoResponse;
import peaksoft.instogram.service.UserInfoService;

@RestController
@RequestMapping("/api/userInfos")
@RequiredArgsConstructor
public class UserInfoApi {
    private final UserInfoService userInfoService;

    //todo save info user
    @PostMapping("/add-info")
    @PreAuthorize("hasRole('USER')")
    public UserInfoResponse addUserInfo(@RequestBody UserInfoRequest userInfoRequest) {
        return userInfoService.saveUserInfo(userInfoRequest);
    }

    //todo get user's info by id
    @GetMapping("/{userId}/get-info")
    public UserInfoResponse getUserInfo(@PathVariable Long userId) {
        return userInfoService.findUserInfoByUserId(userId);
    }

    //todo update info
    @PutMapping("/{userId}/edit-info")
    @PreAuthorize("hasRole('USER')")
    public SimpleResponse updateInfo(@PathVariable Long userId, @RequestBody InfoUpdateRequest infoUpdateRequest) {
        return userInfoService.updateUserInfo(userId, infoUpdateRequest);
    }

    //todo change user's image
    @PutMapping("/{userId}/change-av")
    public SimpleResponse updateUserImage(@PathVariable Long userId, @RequestBody String image) {
        return userInfoService.changeImage(userId, image);
    }

    //todo delete user's avatar
    @DeleteMapping("/{userId}/delete-image")
    @PreAuthorize("hasRole('USER')")
    public SimpleResponse deleteUserInfo(@PathVariable Long userId) {
        return userInfoService.deleteImage(userId);
    }
}
