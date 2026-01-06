package peaksoft.instogram.entity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import peaksoft.instogram.api.FollowerApi;
import peaksoft.instogram.enums.Role;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class User implements UserDetails {
    @Id
    @GeneratedValue(
            strategy = GenerationType.IDENTITY
    )
    Long id;
    @Column(unique = true, nullable = false)
    String userName;
    String password;
    @Column(unique = true, nullable = false)
    String email;
    @Enumerated(EnumType.STRING)
    Role role;
    int followerCount;
    int followingCount;

    @Column(unique = true, nullable = false,
            name = "phone_number",
            length = 13,
            columnDefinition = "varchar(13) check (phone_number LIKE '+996%' AND length(phone_number) = 13)"
    )
    String phoneNumber;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    UserInfo userInfo;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    List<Like> likes = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    Follower followerList;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

}


