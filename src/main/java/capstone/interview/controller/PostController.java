package capstone.interview.controller;

import capstone.interview.model.Comment;
import capstone.interview.model.PostLike;
import capstone.interview.model.Post;
import capstone.interview.model.User;
import capstone.interview.repository.CommentRepository;
import capstone.interview.repository.PostLikeRepository;
import capstone.interview.repository.PostRepository;
import capstone.interview.repository.UserRepository;
import capstone.interview.service.FileStorageService;
import capstone.interview.service.PostService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/community") // 커뮤니티 게시물 관련 경로로 수정
public class PostController {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private final CommentRepository commentRepository;
    private final FileStorageService fileStorageService;
    private final PostLikeRepository postLikeRepository;

    public PostController(FileStorageService fileStorageService, PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, PostLikeRepository postLikeRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.postLikeRepository = postLikeRepository;
        this.fileStorageService = fileStorageService;
    }

    // 글쓰기 페이지로 이동 (경로는 /community/write)
    @GetMapping("/write")
    public String showWriteForm(Model model, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // 로그인 페이지로 리다이렉트
        }

        model.addAttribute("isAuthenticated", authentication.isAuthenticated());
        model.addAttribute("post", new Post());
        return "write-post"; // 작성한 템플릿 파일로 연결
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostMapping("/write")
    public String submitPost(@ModelAttribute Post post,
                             @RequestParam("file") MultipartFile file,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            post.setUser(user);

            // 현재 시간 설정
            post.setCreatedAt(LocalDateTime.now());

            // 파일 업로드 처리
            if (!file.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/uploads/";
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs(); // 업로드 디렉토리가 없으면 생성
                }

                String fileName = file.getOriginalFilename();
                String filePath = "/uploads/" + fileName;
                try {
                    file.transferTo(new File(uploadDir + fileName));
                    post.setFilePath(filePath); // 상대 경로를 데이터베이스에 저장
                } catch (IOException e) {
                    e.printStackTrace();
                    redirectAttributes.addFlashAttribute("error", "파일 업로드 실패");
                    return "redirect:/community/write";
                }
            }

            // 글 저장
            postRepository.save(post);
            return "redirect:/community";
        } else {
            redirectAttributes.addFlashAttribute("error", "사용자를 찾을 수 없습니다.");
            return "redirect:/community/write";
        }
    }

    @GetMapping("/view/{id}")
    public String viewPost(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            // 조회수 증가
            post.setViewCount(post.getViewCount() + 1);
            postRepository.save(post);

            // 댓글 리스트 추가
            List<Comment> comments = commentRepository.findByPostId(id);
            model.addAttribute("comments", comments);
            model.addAttribute("commentCount", comments.size());

            // 현재 게시물과 함께 모델에 추가
            model.addAttribute("post", post);

            // 인증 여부 및 사용자 이름 가져오기
            boolean isAuthenticated = authentication != null && authentication.isAuthenticated() && !"anonymousUser".equals(authentication.getPrincipal());
            model.addAttribute("isAuthenticated", isAuthenticated);

            // 로그인된 사용자와 게시물 작성자가 동일한지 확인하여 모델에 추가
            if (isAuthenticated) {
                String currentUsername = authentication.getName();
                boolean isAuthor = post.getUser().getUsername().equals(currentUsername);
                model.addAttribute("isAuthor", isAuthor);
            } else {
                model.addAttribute("isAuthor", false);
            }

            return "view-post"; // view-post.html로 이동
        } else {
            return "redirect:/community"; // 게시물이 없을 시 커뮤니티 페이지로 리다이렉트
        }
    }

    @Transactional
    @PostMapping("/comment")
    public String submitComment(@RequestParam("postId") Long postId, @RequestParam("content") String content, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            redirectAttributes.addFlashAttribute("error", "로그인 후 댓글을 작성할 수 있습니다.");
            return "redirect:/community/view/" + postId;
        }

        String username = authentication.getName();
        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            Comment comment = new Comment();
            comment.setPost(postRepository.findById(postId).orElse(null));
            comment.setContent(content);
            comment.setUser(user);
            comment.setCreatedAt(LocalDateTime.now());

            // 댓글 저장
            commentRepository.save(comment);

            // 댓글 수 증가
            Post post = postRepository.findById(postId).orElse(null);
            if (post != null) {
                post.setCommentCount(post.getCommentCount() + 1); // commentCount 증가
                postRepository.save(post); // 변경사항 저장
            }
        }

        return "redirect:/community/view/" + postId;
    }




    @PostMapping("/like/{postId}")
    @ResponseBody
    public Map<String, Object> likePost(@PathVariable("postId") Long postId, Authentication authentication) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            String username = authentication.getName();  // 인증된 사용자 이름 가져오기
            Optional<User> userOptional = userRepository.findByUsername(username);

            if (userOptional.isPresent()) {
                User user = userOptional.get();

                Optional<PostLike> existingLike = postLikeRepository.findByPostAndUser(post, user);

                if (existingLike.isPresent()) {
                    // 이미 좋아요를 눌렀으면 좋아요 취소
                    postLikeRepository.delete(existingLike.get());
                    post.setLikeCount(post.getLikeCount() - 1);
                } else {
                    // 좋아요 추가
                    PostLike like = new PostLike();
                    like.setPost(post);
                    like.setUser(user);
                    postLikeRepository.save(like);
                    post.setLikeCount(post.getLikeCount() + 1);
                }

                postRepository.save(post);

                Map<String, Object> response = new HashMap<>();
                response.put("likeCount", post.getLikeCount());
                return response;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다.");
            }
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다.");
    }

    @PostMapping("/delete-comment/{commentId}")
    public String deleteComment(@PathVariable("commentId") Long commentId, Authentication authentication, RedirectAttributes redirectAttributes) {
        Optional<Comment> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            Post post = comment.getPost();

            // 댓글 삭제
            commentRepository.delete(comment);
        }

        return "redirect:/community/view/" + commentOptional.get().getPost().getId();
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long postId, Model model, Authentication authentication) {
        Optional<Post> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            String username = authentication.getName();

            // 게시물 작성자와 로그인한 사용자가 동일한지 확인
            if (!post.getUser().getUsername().equals(username)) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "수정 권한이 없습니다.");
            }

            model.addAttribute("post", post);
            model.addAttribute("isAuthenticated", authentication != null && authentication.isAuthenticated()); // isAuthenticated 설정
            return "edit-post"; // 수정 페이지 템플릿
        } else {
            return "redirect:/community";
        }
    }



    // 게시물 수정 처리
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PostService postService;

    @PostMapping("/edit/{id}")
    public String updatePost(@PathVariable("id") Long postId,
                             @ModelAttribute Post postDetails,
                             @RequestParam(value = "file", required = false) MultipartFile file,
                             Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        String username = authentication.getName();
        postService.updatePost(postId, postDetails, file, username);

        redirectAttributes.addFlashAttribute("message", "게시물이 수정되었습니다.");
        return "redirect:/community/view/" + postId;
    }







    // 게시물 삭제 처리
    @DeleteMapping("/delete/{id}")
    @Transactional
    public String deletePost(@PathVariable Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        Optional<Post> postOptional = postRepository.findById(id);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();
            String username = authentication.getName();

            if (!post.getUser().getUsername().equals(username)) {
                redirectAttributes.addFlashAttribute("error", "삭제 권한이 없습니다.");
                return "redirect:/community";
            }

            // 관련된 좋아요 및 댓글 삭제
            postLikeRepository.deleteByPost(post);
            commentRepository.deleteByPostId(id);

            // 게시물 삭제
            postRepository.delete(post);
            redirectAttributes.addFlashAttribute("success", "게시물이 성공적으로 삭제되었습니다.");
            return "redirect:/community";
        } else {
            redirectAttributes.addFlashAttribute("error", "게시물을 찾을 수 없습니다.");
            return "redirect:/community";
        }
    }


}
