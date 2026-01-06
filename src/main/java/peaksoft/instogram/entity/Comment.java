package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment {
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "comment_gen"
    )
    @SequenceGenerator(name = "comment_gen", sequenceName = "comment_seq", allocationSize = 1)
    @SequenceGenerator(
            name = "comment_gen",
            sequenceName = "comment_seq",
            allocationSize = 1)
     Long id;
     String comment;
     LocalDate createdAt = LocalDate.now();



    @ManyToOne
    @JoinColumn(name="user_id")
     User user;

    @ManyToOne
    @JoinColumn(name = "post_id")
      Post post;
    @OneToMany(mappedBy ="comment", cascade = CascadeType.ALL )
    List<Like> likes = new ArrayList<>();
    }
