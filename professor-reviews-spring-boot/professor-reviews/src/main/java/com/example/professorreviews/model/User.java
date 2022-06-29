package com.example.professorreviews.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

  @JsonProperty("id")
  private final Long id;

  @JsonProperty("fullname")
  private final String fullname;

  @JsonProperty("email")
  private final String email;

  @JsonProperty("username")
  private final String username;

  @JsonProperty("password")
  private final String password;

  @JsonProperty("role")
  private final String role;

  public User(Long id, String fullname, String email, String username, String password,
      String role) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.username = username;
    this.password = password;
    this.role = role;
  }

  public Long getId() {
    return id;
  }

  public String getFullname() {
    return fullname;
  }

  public String getEmail() {
    return email;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getRole() {
    return role;
  }
}
