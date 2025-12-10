package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class Post {
    @Id
    @GeneratedValue(
            generator = "post_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "post_gen",
            sequenceName = "post_seq",
            allocationSize = 1

    )
    Long id;
    String title;
    String description;
    LocalDate createdAt;

    @ManyToOne
    @JoinColumn(name ="user_id")
    User user;

    @OneToMany(mappedBy = "post")
    List<Comment> comments;

    @OneToMany(mappedBy = "post")
    List<Like> likes;

    @OneToMany(mappedBy ="post",
            cascade = CascadeType.ALL )
    List<Image> images;

}




