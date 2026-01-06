package peaksoft.instogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.instogram.entity.Comment;

import java.util.List;



    @Repository
    public interface CommentRepository extends JpaRepository<Comment, Long> {
        @Query("select c from Comment c where c.post.id = :postId")
        List<Comment> findAllByPostId(Long postId);
}
