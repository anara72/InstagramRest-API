package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.ArrayList;
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
            strategy = GenerationType.SEQUENCE, generator = "post_gen"
    )
    @SequenceGenerator(name = "post_gen", sequenceName = "post_seq", allocationSize = 1)
    Long id;
    String title;
    String description;
    LocalDate createdAt = LocalDate.now();

    @ManyToOne
    @JoinColumn(name ="user_id")
    User user;
    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL)
    List<Comment> comments;

    @OneToMany(mappedBy = "post",
            cascade = CascadeType.ALL)
    List<Like> likes;

    @OneToMany(mappedBy ="post",
            cascade = CascadeType.ALL, orphanRemoval = true )
    List<Image> images = new ArrayList<>();
    @ManyToMany
    @JoinTable(name ="post_collabs", joinColumns = @JoinColumn(name="post_id"),
          inverseJoinColumns = @JoinColumn (name = "collab_id"))
    List<User>collabs = new ArrayList<>();


}




