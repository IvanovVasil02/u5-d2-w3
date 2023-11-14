package ivanovvasil.u5d5w2Project.controllers;

import ivanovvasil.u5d5w2Project.entities.User;
import ivanovvasil.u5d5w2Project.exceptions.BadRequestException;
import ivanovvasil.u5d5w2Project.payloads.NewPutUserDTO;
import ivanovvasil.u5d5w2Project.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/employees")
public class UserController {
  @Autowired
  private UsersService usersService;
  
  @GetMapping("")
  public Page<User> getAll(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "15") int size,
                           @RequestParam(defaultValue = "id") String orderBy) {
    return usersService.findAll(page, size, orderBy);
  }


  @GetMapping("/{id}")
  public User findById(@PathVariable int id) {
    return usersService.findById(id);
  }

  @PutMapping("/{id}")
  public User findByIdAndUpdate(@PathVariable int id, @RequestBody @Validated NewPutUserDTO body, BindingResult validation) {
    if (validation.hasErrors()) {
      throw new BadRequestException("Empty or not respected fields", validation.getAllErrors());
    } else {
      try {
        return usersService.findByIdAndUpdate(id, body);
      } catch (MethodArgumentTypeMismatchException e) {
        throw new BadRequestException("Entered id is invalid");
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }

  }

  @PostMapping("/{id}/uploadImg")
  public User uploadImg(@PathVariable int id,
                        @RequestParam("profileImg") MultipartFile body) throws IOException {
    return usersService.uploadImg(id, body);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void findByIdAndDelete(@PathVariable int id) {
    usersService.findByIdAndDelete(id);
  }
}
