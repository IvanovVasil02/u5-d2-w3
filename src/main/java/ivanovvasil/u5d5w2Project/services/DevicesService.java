package ivanovvasil.u5d5w2Project.services;

import ivanovvasil.u5d5w2Project.entities.Device;
import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.enums.DeviceStatus;
import ivanovvasil.u5d5w2Project.enums.DeviceType;
import ivanovvasil.u5d5w2Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w2Project.payloads.NewDeviceDTO;
import ivanovvasil.u5d5w2Project.payloads.NewPutDeviceDTO;
import ivanovvasil.u5d5w2Project.repositories.DevicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class DevicesService {
  @Autowired
  private DevicesRepository devicesRepository;
  @Autowired
  private UsersService usersService;

  //to save blog post whit runner
  public Device saveDeviceRunner(Device body) {
    return devicesRepository.save(body);
  }

  public Device save(NewDeviceDTO body) throws IOException {
    Device newDevice = new Device();
    newDevice.setDeviceType(DeviceType.valueOf(body.deviceType()));
    newDevice.setModel(body.model());
    newDevice.setDeviceStatus(DeviceStatus.AVAILABLE);
    return devicesRepository.save(newDevice);
  }

  public Page<Device> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return devicesRepository.findAll(pageable);
  }

  public List<Device> findAllById(int id) {
    return devicesRepository.findAllByUserId(id);
  }

  public Device findById(int id) throws NotFoundException {
    return devicesRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public void findByIdAndDelete(int id) throws NotFoundException {
    devicesRepository.delete(this.findById(id));
  }

  public Device findByIdAndUpdate(int id, NewPutDeviceDTO body) throws IOException {
    Device found = this.findById(id);
    found.setDeviceType(DeviceType.valueOf(body.deviceType()));
    found.setModel(body.model());
    found.setDeviceStatus(DeviceStatus.valueOf(body.deviceStatus()));
    if (body.user() != null) {
      User user = usersService.findById(body.user());
      found.setUser(user);
    }
    return devicesRepository.save(found);
  }
}
