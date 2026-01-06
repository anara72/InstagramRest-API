package peaksoft.instogram.service;
import peaksoft.instogram.dto.followerDto.response.FollowerUserResponse;
import peaksoft.instogram.dto.followerDto.response.ProfileSubscribeResponse;
import peaksoft.instogram.entity.User;

import java.util.List;

public interface FollowerService {
    List<FollowerUserResponse> search(String name);
    ProfileSubscribeResponse subscribe(Long targetId); //target user - целевые пользователь(не только наши подписчики, но и группа людей которые следят за нашими постами, (т.е. будущие подписчики и клиенты))
    List<FollowerUserResponse> getAllSubscribedByUserId(Long userId);
    List<FollowerUserResponse> getAllSubscriptionsByUserId(Long userId);
}
