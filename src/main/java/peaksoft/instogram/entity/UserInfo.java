package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.instogram.enums.Gender;


@Entity
@Table(name = "userInfos")
@Getter
@Setter
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
 public class UserInfo{
    @Id
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE, generator = "userInfo_gen"
    )
            @SequenceGenerator(name = "userInfo_gen", sequenceName = "userInfo_seq", allocationSize = 1)
    Long id;
    String fullName;
    String biography;
    //@Enumerated(EnumType.STRING)
    Gender gender;
    String image;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(
            name = "user_id",
            nullable = false,
            unique = true
    )
    User user;
}
