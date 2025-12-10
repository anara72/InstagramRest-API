package peaksoft.instogram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instogram.entity.Image;
import peaksoft.instogram.service.LikeService;

public interface ImageRepo extends JpaRepository<Image,Long>{
}
