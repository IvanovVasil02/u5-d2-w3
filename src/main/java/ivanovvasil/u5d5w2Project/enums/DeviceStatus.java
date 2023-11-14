package ivanovvasil.u5d5w2Project.enums;

import java.util.Random;

public enum DeviceStatus {

  AVAILABLE, ASSIGNED, UNDER_MAINTENANCE, DECOMMISSIONED;
  private static final Random rndm = new Random();

  public static DeviceStatus getRandomDeviceStatus() {
    DeviceStatus[] workstations = values();
    return workstations[rndm.nextInt(workstations.length)];
  }
}
