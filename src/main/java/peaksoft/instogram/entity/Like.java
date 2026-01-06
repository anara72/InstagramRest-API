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
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Like {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "like_gen"
    )
            @SequenceGenerator(name = "like_gen", sequenceName = "like_seq", allocationSize = 1)
    Long id;
    boolean isLiked;

    @ManyToOne
    @JoinColumn(name ="user_id")
    User user;

    @ManyToOne
    @JoinColumn(name="post_id")
    Post post;
    @ManyToOne
    @JoinColumn(name="comment_id")
    Comment comment;


}
