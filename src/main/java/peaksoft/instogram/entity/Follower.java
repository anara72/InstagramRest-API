package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "followers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Follower {
    @Id
    @GeneratedValue(
            generator = "follower_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "follower_gen",
            sequenceName = "follower_seq",
            allocationSize = 1)
    Long id;
    @ElementCollection
     List<Long> subscibers = new ArrayList<>();

    @ElementCollection
    private List<Long> subscriptions=new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
