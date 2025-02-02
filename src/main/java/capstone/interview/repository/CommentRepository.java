package capstone.interview.repository;

import capstone.interview.model.Comment;
import capstone.interview.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByPostId(Long postId);
    // Add this method to delete all comments for a post
    void deleteByPostId(Long postId);
}
