package com.example.professorreviews.api;

import com.example.professorreviews.model.User;
import com.example.professorreviews.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.8.100:4200"})
public class UserController {

  private final UserService userService;

  @Autowired
  public UserController(UserService userService) {
    this.userService = userService;
  }

  //Get all users (GET)
  @GetMapping("/pr/getallusers")
  @ResponseBody
  public List<User> getAllUsers() {
    return userService.getAllUsers();
  }

  //Insert user (POST)
  @PostMapping("/pr/insertuser")
  public int insertUser(@RequestBody User user) {
    return userService.insertUser(user);
  }

  //Select user by id (GET)
  @GetMapping("/pr/user/{id}")
  @ResponseBody
  public User selectUserById(@PathVariable("id") Long id) {
    return userService.selectUserById(id);
  }

  //Select user by id (GET)
  @PostMapping("/pr/user/login")
  @ResponseBody
  public User login(@RequestBody Map<String, String> userMap) {
    return userService.login(userMap.get("username"), userMap.get("password"));
  }

  //Update user by id (PUT)
  @PutMapping("/pr/updateuser/{id}")
  public User updateUserById(@PathVariable Long id, @RequestBody User user) {
    return userService.updateUserById(id, user);
  }

  //Update user password by id (PUT)
  @PutMapping("/pr/updatepassword/{id}")
  public int updateUserById(@PathVariable Long id, @RequestBody Map<String, String> userMap) {
    return userService.updatePassword(id, userMap.get("oldpassword"), userMap.get("newpassword"));
  }

  //Delete user by id (DELETE)
  @DeleteMapping("/pr/deleteuser/{id}")
  public int deleteUserById(@PathVariable Long id) {
    return userService.deleteUserById(id);
  }

}
