package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import peaksoft.instogram.Enum.Gender;


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
            generator = "userinfo_gen",
            strategy = GenerationType.SEQUENCE
    )
    @SequenceGenerator(
            name = "userinfo_gen",
            sequenceName = "userinfo_seq",
            allocationSize = 1

    )

    String fullName;
    String biography;
    Gender gender;
    String image;
    @OneToOne
    User user;
}
