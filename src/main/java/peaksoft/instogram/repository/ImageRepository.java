package peaksoft.instogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instogram.entity.Image;

public interface ImageRepository extends JpaRepository<Image,Long>{
}
