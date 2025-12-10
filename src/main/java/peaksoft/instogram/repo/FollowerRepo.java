package peaksoft.instogram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instogram.entity.Follower;

public interface FollowerRepo extends JpaRepository<Follower,Long> {
}
