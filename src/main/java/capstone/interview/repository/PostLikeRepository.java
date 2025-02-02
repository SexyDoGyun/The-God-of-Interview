package capstone.interview.repository;

import capstone.interview.model.PostLike;
import capstone.interview.model.Post;
import capstone.interview.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByPostAndUser(Post post, User user);

    void deleteByPost(Post post);
}
