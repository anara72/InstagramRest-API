package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "followers")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Follower {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "follower_gen"
    )
    @SequenceGenerator(
            name = "follower_gen",
            sequenceName = "follower_seq",
            allocationSize = 1)
    Long id;

    @ManyToMany
    @JoinTable(name = "subscription",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "subscription_id")
    )
    List<User> subscriptions =  new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "subscribers", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "subscriber_id"))
    List<User> subscribers = new ArrayList<>();


    @OneToOne
     User user;

}
