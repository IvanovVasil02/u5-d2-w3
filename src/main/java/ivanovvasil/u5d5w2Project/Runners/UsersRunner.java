package ivanovvasil.u5d5w2Project.Runners;

import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class UsersRunner implements CommandLineRunner {
  @Autowired
  private UsersService usersService;

  @Override
  public void run(String... args) throws Exception {
    for (int i = 0; i < 30; i++) {
      User newUser = User.builder().build();
      usersService.saveRunnerUser(newUser);
    }
  }
}
