package capstone.interview.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
public class Occupation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToMany(mappedBy = "occupation")
    private Set<InterviewQuestion> interviewQuestions;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    // Constructors, Getters and Setters
    public Occupation() {
    }

    public Occupation(String name, Job job) {
        this.name = name;
        this.job = job;
    }
}