package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Table(name = "images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image {
    @Id
    @GeneratedValue(
            generator = "image_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "image_gen",
            sequenceName = "image_seq",
            allocationSize = 1)

    Long id;
    String imagUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

}
