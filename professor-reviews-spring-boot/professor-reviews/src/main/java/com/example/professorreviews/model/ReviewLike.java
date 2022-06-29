package com.example.professorreviews.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReviewLike {

  @JsonProperty("user_id")
  private final Long user_id;

  @JsonProperty("review_id")
  private final Long review_id;

  @JsonProperty("likeinput")
  private final Boolean likeInput;

  @JsonProperty("likes")
  private final Integer likes;

  @JsonProperty("dislikeinput")
  private final Boolean dislikeInput;

  @JsonProperty("dislikes")
  private final Integer dislikes;

  public ReviewLike(Long user_id, Long review_id, Boolean likeInput, Integer likes,
      Boolean dislikeInput, Integer dislikes) {
    this.user_id = user_id;
    this.review_id = review_id;
    this.likeInput = likeInput;
    this.likes = likes;
    this.dislikeInput = dislikeInput;
    this.dislikes = dislikes;
  }

  public Long getUser_id() {
    return user_id;
  }

  public Long getReview_id() {
    return review_id;
  }

  public Boolean getLikeInput() {
    return likeInput;
  }

  public Integer getLikes() {
    return likes;
  }

  public Boolean getDislikeInput() {
    return dislikeInput;
  }

  public Integer getDislikes() {
    return dislikes;
  }
}
