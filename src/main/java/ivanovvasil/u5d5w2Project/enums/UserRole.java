package ivanovvasil.u5d5w2Project.enums;

import java.util.Random;

public enum UserRole {
  USER,
  ADMIN;

  public static UserRole getRandomRole() {
    UserRole[] roles = values();
    return roles[new Random().nextInt(0, roles.length)];
  }
}
