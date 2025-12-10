package peaksoft.instogram.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "likes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Like {
    @Id
    @GeneratedValue(
            generator = "like_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "like_gen",
            sequenceName = "like_seq",
            allocationSize = 1)

    Long id;
    Boolean isLiked;

    @ManyToOne
    @JoinColumn(name ="user_id")
    User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    Post post;
}
