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
            strategy = GenerationType.SEQUENCE, generator = "image_gen"
    )
    @SequenceGenerator(name = "image_gen", sequenceName = "image_seq", allocationSize = 1)
    Long id;
    String imagUrl;

    @ManyToOne
    @JoinColumn(name = "post_id")
     Post post;

}
