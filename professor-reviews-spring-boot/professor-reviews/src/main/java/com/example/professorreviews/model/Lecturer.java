package com.example.professorreviews.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Lecturer {

  @JsonProperty("id")
  private final Long id;

  @JsonProperty("fullname")
  private final String fullname;

  @JsonProperty("email")
  private final String email;

  @JsonProperty("img_src")
  private final String img_src;

  @JsonProperty("averageRate")
  private final Double averageRate;

  @JsonProperty("ratesCount")
  private final Integer ratesCount;

  @JsonProperty("institute_id")
  private final Long institute_id;

  @JsonProperty("institute")
  private final Institute institute;

  @JsonProperty("reviews")
  private final List<Review> reviews;

  public Lecturer(Long id, String fullname, String email, String img_src,
      Double averageRate, Integer ratesCount, Long institute_id,
      Institute institute, List<Review> reviews) {
    this.id = id;
    this.fullname = fullname;
    this.email = email;
    this.img_src = img_src;
    this.averageRate = averageRate;
    this.ratesCount = ratesCount;
    this.institute_id = institute_id;
    this.institute = institute;
    this.reviews = reviews;
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

  public String getImg_src() {
    return img_src;
  }

  public Double getAverageRate() {
    return averageRate;
  }

  public Integer getRatesCount() {
    return ratesCount;
  }

  public Long getInstitute_id() {
    return institute_id;
  }

  public Institute getInstitute() {
    return institute;
  }

  public List<Review> getReviews() {
    return reviews;
  }
}
