package com.example.professorreviews.service;

import com.example.professorreviews.dao.UserDAO;
import com.example.professorreviews.model.User;
import java.util.List;
import javax.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContextException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@Service
public class UserService {

  private final UserDAO userDAO;

  @Autowired
  public UserService(@Qualifier("UserDAO") UserDAO userDAO) {
    this.userDAO = userDAO;
  }

  public int insertUser(User user) {
    return userDAO.insertUser(user);
  }

  public List<User> getAllUsers() {
    return userDAO.getAllUsers();
  }

  public User selectUserById(Long id) {
    return userDAO.selectUserById(id);
  }

  public User login(String username, String password) {

    User user = userDAO.login(username, password);

    if(user != null) {
      if(username.equals(user.getUsername()) && BCrypt.checkpw(password, user.getPassword())) {
        return user;
      }
      else {
        throw new BadCredentialsException("401");
      }
    }

    return null;
  }

  public User updateUserById(Long id, User user) {
    return userDAO.updateUserById(id, user);
  }

  public int updatePassword(Long id, String oldPassword, String newPassword) {
    return userDAO.updatePassword(id, oldPassword, newPassword);
  }

  public int deleteUserById(Long id) {
    return userDAO.deleteUserById(id);
  }
}


