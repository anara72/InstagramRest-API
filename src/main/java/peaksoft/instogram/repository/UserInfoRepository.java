package peaksoft.instogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.instogram.entity.UserInfo;
import java.util.List;
import java.util.Optional;


    @Repository
    public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {
        Optional<UserInfo> findUserInfoByUser_Email(String userEmail);
    }


