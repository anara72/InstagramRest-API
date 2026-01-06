package peaksoft.instogram.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.instogram.entity.Like;

import java.util.List;


@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
        List<Like> findByUser_IdAndPost_Id(Long user_Id, Long post_Id);
        List<Like> findByUser_IdAndComment_Id(Long user_Id, Long comment_Id);

}
