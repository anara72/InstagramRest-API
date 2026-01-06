package peaksoft.instogram.service.Impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import peaksoft.instogram.config.jwt.JwtService;
import peaksoft.instogram.dto.followerDto.response.FollowerUserResponse;
import peaksoft.instogram.dto.followerDto.response.ProfileSubscribeResponse;
import peaksoft.instogram.entity.Follower;
import peaksoft.instogram.entity.User;
import peaksoft.instogram.repository.FollowerRepository;
import peaksoft.instogram.repository.UserRepository;
import peaksoft.instogram.service.FollowerService;

import java.util.ArrayList;
import java.util.List;


@Service
    @Transactional
    @RequiredArgsConstructor
class FollowerServiceImpl implements FollowerService {
        private final FollowerRepository followerRepository;
        private final UserRepository userRepository;
        private final JwtService jwtService;

//    @Override
//    public List<User> search(String name) {
//        return userRepository.searchUserByUsernameOrByFullName(name);
//    }

        @Override
        public List<FollowerUserResponse> search(String name) {
            List<User> users = userRepository.searchUserByUsernameOrByFullName(name);
            return users.stream().map(u -> FollowerUserResponse.builder().username(u.getUsername())
                    .image(u.getUserInfo() == null || u.getUserInfo().getImage() == null ? null : u.getUserInfo().getImage()).build()).toList();
        }

        @Override
        public ProfileSubscribeResponse subscribe(Long targetId) {
            User currentUser= jwtService.checkToken();
            Long userId = currentUser.getId();
            if(userId.equals(targetId)){
                throw new RuntimeException("You can't subscribe to yourself!");
//                throw new AccessIsDeniedException("You can't subscribe to yourself!");
            }

            //Follower follower = followerRepository.findByUser_Id(userId).orElseThrow(() -> new RuntimeException("follower not found")); //в консоли выходит ex-n: follower not found
            //Follower::new = new Follower()
            Follower follower = followerRepository.findByUser_Id(userId).orElseGet(() -> {Follower f = new Follower();
                                                                                            f.setUser(currentUser);
                                                                                            return followerRepository.save(f);
                                                                                                                                });
            User userTarget = userRepository.findById(targetId).orElseThrow(() -> new RuntimeException("target user not found")); //сам user-profile
            List<User> subscriptions = follower.getSubscriptions();
            boolean exists = subscriptions.stream().anyMatch(sub -> sub.getId().equals(targetId));
            if(!exists) {  //если нет - добавляем //!follower.getSubscription().contains(targetId)
                subscriptions.add(userTarget); //подписывается на target
                //subscriptions.add(userTarget); //follower.getSubscription().add(targetId);
                if(userTarget.getFollowerList() == null) {
                    Follower targetFollower = new Follower();
                    targetFollower.setUser(userTarget);
                    userTarget.setFollowerList(targetFollower);
                }
               userTarget.getFollowerList().getSubscribers().add(currentUser); //у target'та появляется подписчик
                //тут изменяется кол-во подписчиков и подписков
                currentUser.setFollowingCount(follower.getSubscriptions().size());
               userTarget.setFollowerCount(userTarget.getFollowerList().getSubscribers().size());

                followerRepository.save(follower);
                userRepository.save(currentUser);
                userRepository.save(userTarget);
                return ProfileSubscribeResponse.builder().targetUserId(targetId).sub(true).btnText("Отменить подписку").build();
            }else { //если есть - удаляем
                //subscriptions.removeIf(u->u.getId().equals(targetId)); //follower.getSubscription().remove(targetId);
                subscriptions.removeIf(u -> u.getId().equals(targetId));
                if(userTarget.getFollowerList() != null) {
                    userTarget.getFollowerList().getSubscribers().removeIf(u->u.getId().equals(userId));
                }
                //count of followers and following:
                currentUser.setFollowingCount(follower.getSubscriptions().size());
               userTarget.setFollowerCount(userTarget.getFollowerList() != null ? userTarget.getFollowerList().getSubscribers().size() : 0);

                followerRepository.save(follower);
                userRepository.save(currentUser);
                userRepository.save(userTarget);
                return ProfileSubscribeResponse.builder().targetUserId(targetId).sub(false).btnText("Подписаться").build();
                //return "Подписаться";
            }
        }

        @Override
        public List<FollowerUserResponse> getAllSubscribedByUserId(Long userId) {
            User currentUser= jwtService.checkToken();
//        if(!userId.equals(currentUser.getId())) { //в том случае если user смотрит только на своих подпичсчиков
//            throw new RuntimeException("This not you profile");
//        }
            return followerRepository.findSubscribers(userId);
        }

        @Override
        public List<FollowerUserResponse> getAllSubscriptionsByUserId(Long userId) {
            User currentUser= jwtService.checkToken();
            return followerRepository.findSubscriptions(userId);
        }
}
