package ivanovvasil.u5d5w2Project.payloads;

import ivanovvasil.u5d5w2Project.enums.DeviceType;
import ivanovvasil.u5d5w2Project.validators.ValidEnum;
import jakarta.validation.constraints.NotEmpty;

public record NewDeviceDTO(

        @ValidEnum(enumClass = DeviceType.class,
                message = "The type of device must be one of these:" +
                        " SMARTPHONE, TABLET, LAPTOP")
        String deviceType,
        @NotEmpty(message = "The device model is a required field")
        String model) {
}
