package peaksoft.instogram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instogram.entity.UserInfo;

public interface UserInfoRepo extends JpaRepository<UserInfo,Long> {
}
