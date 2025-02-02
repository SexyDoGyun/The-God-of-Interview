package capstone.interview.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "file_path")
    private String filePath; // 파일 경로를 저장할 변수

    @Column(name = "view_count")
    private Integer viewCount = 0; // 조회수 필드 기본값 설정

    @Column(name = "comment_count", nullable = false)
    private Integer commentCount = 0; // 기본값을 0으로 설정

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int likeCount = 0;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Integer getViewCount() { // 조회수 getter를 Integer로 변경
        return viewCount == null ? 0 : viewCount; // null일 경우 0을 반환
    }

    public void setViewCount(Integer viewCount) { // 조회수 setter를 Integer로 변경
        this.viewCount = viewCount;
    }

    // Getters and Setters
    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // 좋아요 수 getter
    public int getLikeCount() {
        return likeCount;
    }

    // 좋아요 수 setter
    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
