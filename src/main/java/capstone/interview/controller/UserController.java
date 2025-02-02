package capstone.interview.controller;

import capstone.interview.model.Post;
import capstone.interview.model.User;
import capstone.interview.repository.PostRepository;
import capstone.interview.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    private final UserService userService;
    private final PostRepository postRepository;  // 추가

    @Autowired
    public UserController(UserService userService, PostRepository postRepository) {
        this.userService = userService;
        this.postRepository = postRepository;
    }

    @GetMapping("/")
    public String showIndexPage(Model model) {
        // 게시물 리스트 가져오기
        List<Post> latestPosts = postRepository.findTop6ByOrderByCreatedAtDesc();

        // 각 게시물에 대한 댓글 수 추가
        for (Post post : latestPosts) {
            int commentCount = postRepository.countCommentsByPostId(post.getId());
            post.setCommentCount(commentCount); // 댓글 수를 Post 객체에 설정
        }

        model.addAttribute("latestPosts", latestPosts);  // 모델에 추가

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("isAuthenticated", authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String));
        return "index";
    }


    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute User user,
            @RequestParam("verificationCode") String verificationCode,
            @RequestParam("birthYear") String birthYear,
            @RequestParam("birthMonth") String birthMonth,
            @RequestParam("birthDay") String birthDay,
            HttpSession session,
            Model model
    ) {
        String sessionCode = (String) session.getAttribute("emailVerificationCode");
        System.out.println("Session code: " + sessionCode);
        System.out.println("Entered code: " + verificationCode);

        if (sessionCode != null && sessionCode.equals(verificationCode)) {
            System.out.println("Verification code matched.");
            String birthDate = birthYear + "-" + birthMonth + "-" + birthDay;
            user.setBirthDate(birthDate);
            userService.save(user);
            return "redirect:/login";
        } else {
            System.out.println("Verification code did not match or is null.");
            model.addAttribute("error", "Invalid verification code");
            return "register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/loginSuccess")
    public String loginSuccess(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "redirect:/"; // 또는 로그인 후 보여줄 페이지
    }



    @GetMapping("/check-username")
    @ResponseBody
    public Map<String, Boolean> checkUsername(@RequestParam("username") String username) {
        boolean exists = userService.findByUsername(username).isPresent();
        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);
        return response;
    }


    @GetMapping("/profile")
    public String myPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            logger.info("UserDetails is null, redirecting to login page.");
            return "redirect:/login";
        }

        String username = userDetails.getUsername();
        logger.info("Authenticated user's username: {}", username);
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            return "profile"; // 템플릿 파일 이름
        } else {
            logger.warn("User not found for username: {}", username);
            return "redirect:/error";
        }
    }


}