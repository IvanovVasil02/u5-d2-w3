package ivanovvasil.u5d5w2Project.enums;

import java.util.Random;

public enum DeviceType {
  SMARTPHONE, TABLET, LAPTOP;
  private static final Random rndm = new Random();

  public static DeviceType getRandomDeviceType() {
    DeviceType[] workstations = values();
    return workstations[rndm.nextInt(workstations.length)];
  }
}
