package peaksoft.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@Getter@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User extends BaseEntity{
    private String fullName;
    @Column(unique = true)
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToMany(mappedBy = "user")
    private List<Feedback> feedbacks = new ArrayList<>();

    public void addFeedback(Feedback feedback) {
        if(feedback != null){
            feedbacks.add((feedback));
            feedback.setUser(this);
        }else {
            throw new RuntimeException("Feedback is null");
        }
    }
}
