package ivanovvasil.u5d5w2Project.controllers;

import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w2Project.exceptions.UnauthorizedException;
import ivanovvasil.u5d5w2Project.payloads.NewUserDTO;
import ivanovvasil.u5d5w2Project.payloads.UserLoggedTokenDTO;
import ivanovvasil.u5d5w2Project.payloads.UserLoginDTO;
import ivanovvasil.u5d5w2Project.services.AuthenticationService;
import ivanovvasil.u5d5w2Project.services.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/authentication")
public class UserAunthenticationController {
  @Autowired
  private AuthenticationService authenticationService;
  @Autowired
  private UsersService usersService;

  @PostMapping("/login")
  public UserLoggedTokenDTO login(@RequestBody @Validated UserLoginDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return new UserLoggedTokenDTO(authenticationService.authenticateUser(body));
      } catch (UnauthorizedException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public User saveUser(@RequestBody @Validated @Valid NewUserDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return usersService.save(body);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

}
