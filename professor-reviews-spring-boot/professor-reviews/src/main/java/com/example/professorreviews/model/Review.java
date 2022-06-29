package com.example.professorreviews.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Review {

  @JsonProperty("id")
  private final Long id;

  @JsonProperty("user_id")
  private final Long user_id;

  @JsonProperty("user")
  private final User user;

  @JsonProperty("lecturer_id")
  private final Long lecturer_id;

  @JsonProperty("lecturer")
  private final Lecturer lecturer;

  @JsonProperty("useful_l")
  private final Integer useful_l;

  @JsonProperty("interesting_l")
  private final Integer interesting_l;

  @JsonProperty("material")
  private final Integer material;

  @JsonProperty("commitment")
  private final Integer commitment;

  @JsonProperty("communication")
  private final Integer communication;

  @JsonProperty("comment")
  private final String comment;

  @JsonProperty("date")
  private final String date;

  @JsonProperty("likes")
  private final Integer likes;

  @JsonProperty("dislikes")
  private final Integer dislikes;

  public Review(Long id, Long user_id, User user, Long lecturer_id,
      Lecturer lecturer, Integer useful_l, Integer interesting_l, Integer material,
      Integer commitment, Integer communication, String comment, String date, Integer likes,
      Integer dislikes) {
    this.id = id;
    this.user_id = user_id;
    this.user = user;
    this.lecturer_id = lecturer_id;
    this.lecturer = lecturer;
    this.useful_l = useful_l;
    this.interesting_l = interesting_l;
    this.material = material;
    this.commitment = commitment;
    this.communication = communication;
    this.comment = comment;
    this.date = date;
    this.likes = likes;
    this.dislikes = dislikes;
  }

  public Long getId() {
    return id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public User getUser() {
    return user;
  }

  public Long getLecturer_id() {
    return lecturer_id;
  }

  public Lecturer getLecturer() {
    return lecturer;
  }

  public Integer getUseful_l() {
    return useful_l;
  }

  public Integer getInteresting_l() {
    return interesting_l;
  }

  public Integer getMaterial() {
    return material;
  }

  public Integer getCommitment() {
    return commitment;
  }

  public Integer getCommunication() {
    return communication;
  }

  public String getComment() {
    return comment;
  }

  public String getDate() {
    return date;
  }

  public Integer getLikes() {
    return likes;
  }

  public Integer getDislikes() {
    return dislikes;
  }
}
