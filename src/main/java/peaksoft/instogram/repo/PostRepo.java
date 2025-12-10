package peaksoft.instogram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instogram.entity.Post;

public interface PostRepo extends JpaRepository<Post,Long> {
}
