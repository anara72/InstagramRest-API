package peaksoft.instogram.service;

import peaksoft.instogram.dto.SimpleResponse;
import peaksoft.instogram.dto.userInfoDto.request.InfoUpdateRequest;
import peaksoft.instogram.dto.userInfoDto.request.UserInfoRequest;
import peaksoft.instogram.dto.userInfoDto.response.UserInfoResponse;

public interface UserInfoService {

    UserInfoResponse saveUserInfo(UserInfoRequest userInfoRequest);

    UserInfoResponse findUserInfoByUserId(Long userId);
    SimpleResponse updateUserInfo(Long userId, InfoUpdateRequest infoUpdateRequest);
    SimpleResponse changeImage(Long userId, String avatar);
    SimpleResponse deleteImage(Long userId);

}
