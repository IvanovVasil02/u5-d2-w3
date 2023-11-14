package ivanovvasil.u5d5w2Project.entities;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w2Project.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(builderClassName = "UsersBuilder")
//@JsonIgnoreProperties("password")
@ToString
public class User implements UserDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private String name;
  private String surname;
  private String email;
  private String password;
  private String profilePicture;
  @Enumerated(EnumType.STRING)
  private UserRole role;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return id == user.id && Objects.equals(name, user.name) && Objects.equals(surname, user.surname) && Objects.equals(email, user.email) && Objects.equals(profilePicture, user.profilePicture);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.name()));
  }

  @Override
  public String getUsername() {
    return this.email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public static class UsersBuilder {
    Faker f = new Faker(Locale.ITALY);
    private String name = f.name().name();
    private String surname = f.name().lastName();
    private String email = f.internet().emailAddress();
    private String password = f.internet().password();
    private String profilePicture = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTC0HlQ_ckX6HqCAlqroocyRDx_ZRu3x3ezoA&usqp=CAU";
    private UserRole role = UserRole.getRandomRole();

  }
}


