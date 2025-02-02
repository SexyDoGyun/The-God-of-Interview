package capstone.interview.controller;

import capstone.interview.model.Post;
import capstone.interview.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CommunityController {

    private final PostRepository postRepository;

    public CommunityController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping("/community")
    public String showCommunityPage(Model model,
                                    @RequestParam(value = "page", defaultValue = "0") int page, // 현재 페이지
                                    @RequestParam(value = "size", defaultValue = "7") int size, // 페이지당 게시물 수
                                    Authentication authentication) {
        // 인증 여부 확인
        boolean isAuthenticated = authentication != null && authentication.isAuthenticated();
        model.addAttribute("isAuthenticated", isAuthenticated);

        // 페이지네이션을 위한 Pageable 객체 생성
        Pageable pageable = PageRequest.of(page, size);

        // 페이지네이션된 게시물 목록 가져오기
        Page<Post> postPage = postRepository.findAll(pageable);

        // 각 게시물의 댓글 수를 가져와서 post에 추가
        List<Post> postsWithComments = postPage.stream()
                .peek(post -> post.setCommentCount(postRepository.countCommentsByPostId(post.getId())))
                .collect(Collectors.toList());

        model.addAttribute("posts", postsWithComments); // 댓글 수가 추가된 게시물 리스트
        model.addAttribute("currentPage", page); // 현재 페이지
        model.addAttribute("totalPages", postPage.getTotalPages()); // 전체 페이지 수

        return "community"; // 커뮤니티 페이지 템플릿 이름
    }

}
