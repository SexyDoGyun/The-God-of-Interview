package capstone.interview.repository;

import capstone.interview.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAll(Pageable pageable);

    // 특정 게시물의 likeCount를 가져오는 메서드 추가
    @Query("SELECT p.likeCount FROM Post p WHERE p.id = :postId")
    int getLikeCountById(@Param("postId") Long postId);

    @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
    int countCommentsByPostId(@Param("postId") Long postId);

    List<Post> findTop6ByOrderByCreatedAtDesc();

}
