package com.example.professorreviews.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AverageRates {

  @JsonProperty("useful_l")
  private final Double useful_l;

  @JsonProperty("interesting_l")
  private final Double interesting_l;

  @JsonProperty("material")
  private final Double material;

  @JsonProperty("commitment")
  private final Double commitment;

  @JsonProperty("communication")
  private final Double communication;

  @JsonProperty("ratesCount")
  private final Integer ratesCount;

  public AverageRates(Double useful_l, Double interesting_l, Double material,
      Double commitment, Double communication, Integer ratesCount) {
    this.useful_l = useful_l;
    this.interesting_l = interesting_l;
    this.material = material;
    this.commitment = commitment;
    this.communication = communication;
    this.ratesCount = ratesCount;
  }

  public Double getUseful_l() {
    return useful_l;
  }

  public Double getInteresting_l() {
    return interesting_l;
  }

  public Double getMaterial() {
    return material;
  }

  public Double getCommitment() {
    return commitment;
  }

  public Double getCommunication() {
    return communication;
  }

  public Integer getRatesCount() {
    return ratesCount;
  }
}
