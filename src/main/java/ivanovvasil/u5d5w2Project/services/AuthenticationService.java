package ivanovvasil.u5d5w2Project.services;

import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.exceptions.AccesDeniedException;
import ivanovvasil.u5d5w2Project.payloads.UserLoginDTO;
import ivanovvasil.u5d5w2Project.security.JWTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
  @Autowired
  UsersService usersService;
  @Autowired
  JWTools jwTools;

  public String authenticateUser(UserLoginDTO body) {
    User foundUser = usersService.findByEmail(body.email());
    if (foundUser.getPassword().equals(body.password())) {
      return jwTools.createToken(foundUser);
    } else {
      throw new AccesDeniedException("Invalid credentials");
    }
  }
}
