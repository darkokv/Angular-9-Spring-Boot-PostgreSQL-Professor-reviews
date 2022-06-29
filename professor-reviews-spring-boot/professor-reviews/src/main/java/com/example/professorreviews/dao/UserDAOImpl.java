package com.example.professorreviews.dao;

import com.example.professorreviews.model.User;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.InternalServerErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository("UserDAO")
public class UserDAOImpl implements UserDAO{

  public static final String SQL_SELECT_USER_BY_ID =
      "select * from pr.user where id = :id";

  public static final String SQL_SELECT_USER_IDS =
      "select id from pr.user order by id";

  public static final String SQL_LOGIN_USER =
      "select * from pr.user where username = :username";

  public static final String SQL_INSERT_USER =
      "insert into pr.user (id, fullname, email, username, password, role)" +
          " values (nextval('pr.pr_user_id_seq'), :fullname, :email, :username, :password, :role)";

  public static final String SQL_UPDATE_USER_BY_ID =
      "update pr.user" +
          " set fullname = :fullname, email = :email, username = :username, password = :password" +
          " where id = :id";

  public static final String SQL_UPDATE_PASSWORD_BY_ID =
      "update pr.user" +
          " set password = :password" +
          " where id = :id";

  public static final String SQL_DELETE_USER_BY_ID =
      "delete from pr.user where id = :id";




  private static final Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

  private final NamedParameterJdbcTemplate njdbcTemplate;

  public static final RowMapper<User> MAPPER_SELECT_USER = new RowMapper<User>() {
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User user = new User(
          rs.getLong("id"),
          rs.getString("fullname"),
          rs.getString("email"),
          rs.getString("username"),
          rs.getString("password"),
          rs.getString("role")
      );
      return user;
    }
  };

  public static final RowMapper<Long> MAPPER_SELECT_IDS = new RowMapper<Long>() {

    @Override
    public Long mapRow(ResultSet rs, int rowNum) throws SQLException {
      return rs.getLong("id");
    }
  };

  @Autowired
  public UserDAOImpl(
      NamedParameterJdbcTemplate njdbcTemplate) {
    this.njdbcTemplate = njdbcTemplate;
  }


  @Override
  @Transactional
  public int insertUser(Long id, User user) {
    KeyHolder keyHolder = new GeneratedKeyHolder();
    List<User> allUsers = getAllUsers();

    //Check if user exists
    for (User usr : allUsers) {
      if (usr.getUsername().equals(user.getUsername())) {
        logger.debug("Username is already taken ", user.getUsername());
        throw new BadRequestException("409");
      }
    }

    MapSqlParameterSource params = mapParams(user);
    int rowCnt = njdbcTemplate.update(SQL_INSERT_USER, params, keyHolder, new String[] {"id"});

    return rowCnt;
  }

  @Transactional
  @Override
  public List<User> getAllUsers() {

    List<Long> userIds = njdbcTemplate.query(SQL_SELECT_USER_IDS, MAPPER_SELECT_IDS);

    List<User> res = new ArrayList<User>();

    if (userIds==null || userIds.isEmpty()) {
      logger.debug("Could not find any user.");
      return res;
    }

    for (Long userId: userIds) {
      User user = selectUserById(userId);
      if (userId!=null) {
        res.add(user);
      }
    }

    return res;
  }

  @Override
  public User selectUserById(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    List<User> users = njdbcTemplate.query(SQL_SELECT_USER_BY_ID, params, MAPPER_SELECT_USER);

    if(users == null  || users.isEmpty()) {
      logger.debug("Could not find user with id {}.", id);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (users.size()>1) {
        logger.debug("Got more than 1 result for id {}.", id);
      }
    }

    User user = users.get(0);

    return user;
  }

  @Override
  public User login(String username, String password) {

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("username", username);

    List<User> users = njdbcTemplate.query(SQL_LOGIN_USER, params, MAPPER_SELECT_USER);

    if(users == null  || users.isEmpty()) {
      logger.debug("Could not find user with username {}.", username);
      return null;
    }

    if (logger.isDebugEnabled()) {
      if (users.size()>1) {
        logger.debug("Got more than 1 result for username {}.", username);
      }
    }

    User user = users.get(0);

    return user;
  }

  @Transactional
  @Override
  public User updateUserById(Long id, User user) {
    User existingUser = selectUserById(id);

    List<User> allUsers = getAllUsers();

    //Check if user exists
    if(!existingUser.getUsername().equals(user.getUsername())) {
      for (User usr : allUsers) {
        if (usr.getUsername().equals(user.getUsername())) {
          logger.debug("Username is already taken ", user.getUsername());
          return null;
        }
      }
    }

    MapSqlParameterSource params = mapParams(user);
    params.addValue("id", id);

    njdbcTemplate.update(SQL_UPDATE_USER_BY_ID, params);

    return selectUserById(id);
  }

  @Transactional
  @Override
  public int updatePassword(Long id, String oldPassword, String newPassword) {
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    User existingUser = selectUserById(id);

    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);
    params.addValue("password", passwordEncoder.encode(newPassword));

    if(BCrypt.checkpw(oldPassword, existingUser.getPassword())) {
      int rowCnt = njdbcTemplate.update(SQL_UPDATE_PASSWORD_BY_ID, params);
      return rowCnt;
    }
    else {
      throw new BadCredentialsException("400");
    }
  }

  @Transactional
  @Override
  public int deleteUserById(Long id) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    params.addValue("id", id);

    logger.debug("Deleting user with id {}.", id);

    return njdbcTemplate.update(SQL_DELETE_USER_BY_ID, params);
  }

  private static MapSqlParameterSource mapParams(User user) {
    MapSqlParameterSource params = new MapSqlParameterSource();
    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

    // id is disregarded for inserts and can also be missing from object
    if (user.getId()!=null) {
      params.addValue("id", user.getId());
    }

    params.addValue("fullname", user.getFullname());
    params.addValue("email", user.getEmail());
    params.addValue("username", user.getUsername());
    params.addValue("password", passwordEncoder.encode(user.getPassword()));
    params.addValue("role", user.getRole());

    return params;
  }
}
