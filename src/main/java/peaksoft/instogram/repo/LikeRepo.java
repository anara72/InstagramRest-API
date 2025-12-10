package peaksoft.instogram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instogram.entity.Like;
import peaksoft.instogram.service.LikeService;

public interface LikeRepo extends JpaRepository<Like,Long> {
}
