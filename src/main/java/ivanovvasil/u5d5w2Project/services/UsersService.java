package ivanovvasil.u5d5w2Project.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import ivanovvasil.u5d5w2Project.entities.Device;
import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.enums.DeviceStatus;
import ivanovvasil.u5d5w2Project.exceptions.NotFoundException;
import ivanovvasil.u5d5w2Project.payloads.NewPutUserDTO;
import ivanovvasil.u5d5w2Project.repositories.DevicesRepository;
import ivanovvasil.u5d5w2Project.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class UsersService {
  @Autowired
  private UsersRepository usersRepository;
  @Autowired
  private DevicesRepository devicesRepository;
  @Autowired
  private Cloudinary cloudinary;

  //findALl for employees runner
  public User saveRunnerUser(User employee) {
    return usersRepository.save(employee);
  }


  //findALl for employees runner
  public List<User> findAll() {
    return usersRepository.findAll();
  }

  public Page<User> findAll(int page, int size, String orderBy) {
    Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));
    return usersRepository.findAll(pageable);
  }

  public User findById(int id) throws NotFoundException {
    return usersRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
  }

  public User findByEmail(String email) {
    return usersRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("No user found with this email: " + email));
  }

  public void findByIdAndDelete(int id) throws NotFoundException {
    List<Device> devicesList = devicesRepository.findAllByUserId(id);
    devicesList.forEach(device -> {
      device.setDeviceStatus(DeviceStatus.AVAILABLE);
      device.setUser(null);
      devicesRepository.save(device);
    });
    usersRepository.delete(this.findById(id));
  }

  public User findByIdAndUpdate(int id, NewPutUserDTO body) throws IOException {
    User found = this.findById(id);
    found.setName(body.name());
    found.setSurname(body.surname());
    found.setEmail(body.email());
    return usersRepository.save(found);
  }

  public User uploadImg(int id, MultipartFile file) throws IOException {
    User found = this.findById(id);
    String urlImg = (String) cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap()).get("url");
    found.setProfilePicture(urlImg);
    usersRepository.save(found);
    return found;
  }
}
