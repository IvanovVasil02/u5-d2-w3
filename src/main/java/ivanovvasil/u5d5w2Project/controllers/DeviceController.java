package ivanovvasil.u5d5w2Project.controllers;

import ivanovvasil.u5d5w2Project.entities.Device;
import ivanovvasil.u5d5w2Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w2Project.payloads.NewDeviceDTO;
import ivanovvasil.u5d5w2Project.payloads.NewPutDeviceDTO;
import ivanovvasil.u5d5w2Project.services.DevicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/devices")
public class DeviceController {
  @Autowired
  private DevicesService devicesService;

  @PostMapping("")
  @ResponseStatus(HttpStatus.CREATED)
  public Device saveDevice(@RequestBody @Validated NewDeviceDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return devicesService.save(body);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @GetMapping("")
  public Page<Device> getAll(@RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "15") int size,
                             @RequestParam(defaultValue = "id") String orderBy) {
    return devicesService.findAll(page, size, orderBy);
  }

  @GetMapping("/{id}")
  public Device findById(@PathVariable int id) {
    return devicesService.findById(id);
  }

  @PutMapping("/{id}")
  public Device findByIdAndUpdate(@PathVariable int id, @RequestBody @Validated NewPutDeviceDTO body, BindingResult validation) {

    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return devicesService.findByIdAndUpdate(id, body);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void findByIdAndDelete(@PathVariable int id) {
    devicesService.findByIdAndDelete(id);
  }
}
