package peaksoft.instogram.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import peaksoft.instogram.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment,Long> {
}
