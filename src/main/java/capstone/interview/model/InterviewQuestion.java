package capstone.interview.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InterviewQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String industry;
    private String job;
    private String question;

    @ManyToOne
    @JoinColumn(name = "occupation_id")
    private Occupation occupation;
}
