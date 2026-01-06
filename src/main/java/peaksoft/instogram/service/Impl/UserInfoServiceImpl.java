package peaksoft.instogram.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import peaksoft.instogram.config.jwt.JwtService;
import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.userInfoDto.request.InfoUpdateRequest;
import peaksoft.instogram.dto.userInfoDto.response.UserInfoResponse;
import peaksoft.instogram.entity.User;
import peaksoft.instogram.entity.UserInfo;
import peaksoft.instogram.repository.UserInfoRepository;
import peaksoft.instogram.repository.UserRepository;
import peaksoft.instogram.dto.userInfoDto.request.UserInfoRequest;
import peaksoft.instogram.service.UserInfoService;
@Service
@Transactional
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    private final UserInfoRepository userInfoRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;


    @Override
    public UserInfoResponse saveUserInfo(UserInfoRequest userInfoRequest) {
        User currentUser = jwtService.checkToken();
        if(currentUser.getUserInfo() != null){
            throw new RuntimeException("Info about User already exists");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setFullName(userInfoRequest.fullName());
        userInfo.setBiography(userInfoRequest.biography());
        userInfo.setGender(userInfoRequest.gender());
        userInfo.setImage(userInfoRequest.image());
        userInfo.setUser(currentUser);
        currentUser.setUserInfo(userInfo);
        userRepository.save(currentUser);
        return UserInfoResponse.builder().fullName(userInfo.getFullName()).biography(userInfo.getBiography()).gender(userInfo.getGender()).image(userInfo.getImage()).build();
    }

    @Override
    public UserInfoResponse findUserInfoByUserId(Long userId) {
        User currentUser = jwtService.checkToken();
        if(!currentUser.getId().equals(userId)){
            throw new RuntimeException("This user is not the current user");
        }
        UserInfo userInfo = currentUser.getUserInfo();
        if(userInfo == null){
            return  UserInfoResponse.builder()
                    .fullName("").biography("")
                    .gender(null).image(null)
                    .build();
        }
        return UserInfoResponse.builder()
                .fullName(userInfo.getFullName())
                .biography(userInfo.getBiography())
                .gender(userInfo.getGender())
                .image(userInfo.getImage())
                .build();
    }

    @Override
    public SimpleResponse updateUserInfo(Long userId, InfoUpdateRequest infoUpdateRequest) {
        User currentUser = jwtService.checkToken();
        if(!currentUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't update another user-info");
        }
        UserInfo userInfo = currentUser.getUserInfo();
        if(userInfo == null){
            //create a new info if user doesn't have the userInfo
            userInfo = new UserInfo();
            userInfo.setUser(currentUser);
            currentUser.setUserInfo(userInfo);
        }
        userInfo.setFullName(infoUpdateRequest.fullName());
        userInfo.setBiography(infoUpdateRequest.biography());
        userInfo.setGender(infoUpdateRequest.gender());
        userRepository.save(currentUser);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("user's info is updated")
                .build();
    }

    @Override
    public SimpleResponse changeImage(Long userId, String avatar) {
        User currentUser = jwtService.checkToken();
        if(!currentUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't update another user-info");
        }
        UserInfo userInfo = currentUser.getUserInfo();
        if(userInfo.getImage() == null){
            userInfo = new UserInfo();
            userInfo.setImage(avatar);
            currentUser.setUserInfo(userInfo);
            return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("user's image is added").build();
        }
        userInfo.setImage(avatar);
        userInfoRepository.save(userInfo);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("user's avatar is changed")
                .build();
    }

    @Override
    public SimpleResponse deleteImage(Long userId) {
        User currentUser = jwtService.checkToken();
        if(!currentUser.getId().equals(userId)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You can't delete another user-info");
        }
        UserInfo userInfo = currentUser.getUserInfo();
        if(userInfo == null || userInfo.getImage() == null || userInfo.getImage().isBlank()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user's image is empty");
        }
        userInfo.setImage(null);
        userInfoRepository.save(userInfo);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("user's avatar is deleted")
                .build();
    }
}