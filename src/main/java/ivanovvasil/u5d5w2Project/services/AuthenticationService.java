package ivanovvasil.u5d5w2Project.services;

import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w2Project.exceptions.UnauthorizedException;
import ivanovvasil.u5d5w2Project.payloads.NewUserDTO;
import ivanovvasil.u5d5w2Project.payloads.UserLoginDTO;
import ivanovvasil.u5d5w2Project.repositories.UsersRepository;
import ivanovvasil.u5d5w2Project.security.JWTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class AuthenticationService {
  @Autowired
  private UsersService usersService;
  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private JWTools jwTools;
  @Autowired
  private PasswordEncoder passwordEncoder;

  public User registerUser(NewUserDTO body) throws IOException {
    usersRepository.findByEmail(body.email()).ifPresent(author -> {
      throw new BadRequestException("The email  " + author.getEmail() + " is already used!");
    });
    User newUser = new User();
    newUser.setName(body.name());
    newUser.setSurname(body.surname());
    newUser.setEmail(body.email());
    newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
    if (body.profilePicture() != null) {
      newUser.setProfilePicture(body.profilePicture());
    } else {
      newUser.setProfilePicture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTC0HlQ_ckX6HqCAlqroocyRDx_ZRu3x3ezoA&usqp=CAU");
    }
    return usersRepository.save(newUser);
  }

  public String authenticateUser(UserLoginDTO body) {
    User foundUser = usersService.findByEmail(body.email());
    if (passwordEncoder.matches(body.password(), foundUser.getPassword())) {
      return jwTools.createToken(foundUser);
    } else {
      throw new UnauthorizedException("Invalid credentials");
    }
  }
}
