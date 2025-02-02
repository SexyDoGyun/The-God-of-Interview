package capstone.interview.service;

import capstone.interview.model.Post;
import capstone.interview.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private FileStorageService fileStorageService;

    @Transactional
    public void updatePost(Long postId, Post postDetails, MultipartFile file, String username) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // 게시물 작성자 확인
            if (!post.getUser().getUsername().equals(username)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
            }

            // 게시물 내용 업데이트
            post.setTitle(postDetails.getTitle());
            post.setContent(postDetails.getContent());
            post.setUpdatedAt(LocalDateTime.now());

            // 파일 처리
            if (file != null && !file.isEmpty()) {
                String filePath = fileStorageService.saveFile(file);
                post.setFilePath("/uploads/" + filePath);
            }

            // 변경 사항 저장
            postRepository.save(post);
        }
    }
}
