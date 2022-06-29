package com.example.professorreviews.dao;

import com.example.professorreviews.model.User;
import java.util.List;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

public interface UserDAO {

  @Transactional
  int insertUser(Long id, User user);

  @Transactional
  default int insertUser(User user) {
    Long id = new Random().nextLong();
    return insertUser(id, user);
  }

  @Transactional
  List<User> getAllUsers();

  @Transactional
  User selectUserById(Long id);

  @Transactional
  User login(String username, String password);

  @Transactional
  User updateUserById(Long id, User user);

  @Transactional
  int updatePassword(Long id, String oldPassword, String newPassword);

  @Transactional
  int deleteUserById(Long id);

}
