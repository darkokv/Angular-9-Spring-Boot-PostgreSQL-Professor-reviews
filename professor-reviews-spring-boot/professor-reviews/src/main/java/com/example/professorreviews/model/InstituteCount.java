package com.example.professorreviews.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class InstituteCount {

  @JsonProperty("count")
  private final Integer count;

  @JsonProperty("city")
  private final String city;

  public InstituteCount(Integer count, String city) {
    this.count = count;
    this.city = city;
  }

  public Integer getCount() {
    return count;
  }

  public String getCity() {
    return city;
  }
}
