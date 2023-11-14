package ivanovvasil.u5d5w2Project.entities;

import com.github.javafaker.Faker;
import ivanovvasil.u5d5w2Project.enums.DeviceStatus;
import ivanovvasil.u5d5w2Project.enums.DeviceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Locale;
import java.util.Objects;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder(builderClassName = "DeviceBuilder")
public class Device {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Enumerated(EnumType.STRING)
  private DeviceType deviceType;
  private String Model;
  @Enumerated(EnumType.STRING)
  private DeviceStatus deviceStatus;
  @ManyToOne
  @JoinColumn(name = "employee_id")
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Device device = (Device) o;
    return id == device.id && deviceType == device.deviceType && Objects.equals(Model, device.Model) && deviceStatus == device.deviceStatus && Objects.equals(user, device.user);
  }

  public static class DeviceBuilder {
    Faker f = new Faker(Locale.ITALY);
    private DeviceType deviceType = DeviceType.getRandomDeviceType();
    private String Model = f.team().name();
  }
}
