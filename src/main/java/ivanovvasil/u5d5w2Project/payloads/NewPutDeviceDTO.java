package ivanovvasil.u5d5w2Project.payloads;

import ivanovvasil.u5d5w2Project.enums.DeviceStatus;
import ivanovvasil.u5d5w2Project.enums.DeviceType;
import ivanovvasil.u5d5w2Project.validators.ValidEnum;
import jakarta.validation.constraints.NotEmpty;

public record NewPutDeviceDTO(
        @ValidEnum(enumClass = DeviceType.class,
                message = "The type of device must be one of these:" +
                        " SMARTPHONE, TABLET, LAPTOP")
        String deviceType,
        @NotEmpty(message = "The device model field cannot be empty")
        String model,
        @ValidEnum(enumClass = DeviceStatus.class,
                message = "The status that can be assigned to the device " +
                        "can only be one of the following:" +
                        "AVAILABLE, ASSIGNED, UNDER_MAINTENANCE, DECOMMISSIONED")
        String deviceStatus,

        Integer user
) {
}
