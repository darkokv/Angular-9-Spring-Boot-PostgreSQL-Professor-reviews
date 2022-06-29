package com.example.professorreviews.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Institute {

  @JsonProperty("id")
  private final Long id;

  @JsonProperty("name")
  private final String name;

  @JsonProperty("adress")
  private final String adress;

  @JsonProperty("city")
  private final String city;

  @JsonProperty("img_cover_src")
  private final String img_cover_src;

  @JsonProperty("averageRate")
  private final Double averageRate;

  @JsonProperty("ratesCount")
  private final Integer ratesCount;

  @JsonProperty("lecturers")
  private final List<Lecturer> lecturers;

  public Institute(Long id, String name, String adress, String city, String img_cover_src,
      Double averageRate, Integer ratesCount,
      List<Lecturer> lecturers) {
    this.id = id;
    this.name = name;
    this.adress = adress;
    this.city = city;
    this.img_cover_src = img_cover_src;
    this.averageRate = averageRate;
    this.ratesCount = ratesCount;
    this.lecturers = lecturers;
  }

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getAdress() {
    return adress;
  }

  public String getCity() {
    return city;
  }

  public String getImg_cover_src() {
    return img_cover_src;
  }

  public Double getAverageRate() {
    return averageRate;
  }

  public Integer getRatesCount() {
    return ratesCount;
  }

  public List<Lecturer> getLecturers() {
    return lecturers;
  }
}
