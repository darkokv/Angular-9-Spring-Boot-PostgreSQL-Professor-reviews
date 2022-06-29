package com.example.professorreviews.service;

import com.example.professorreviews.dao.ReviewLikeDAO;
import com.example.professorreviews.model.Review;
import com.example.professorreviews.model.ReviewLike;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewLikeService {

  private final ReviewLikeDAO reviewLikeDAO;

  @Autowired
  public ReviewLikeService(ReviewLikeDAO reviewLikeDAO) {
    this.reviewLikeDAO = reviewLikeDAO;
  }

  public int insertReviewLike(ReviewLike reviewLike) {
    return reviewLikeDAO.insertReviewLike(reviewLike);
  }

  public ReviewLike selectReviewLike(Long user_id, Long review_id) {
    return reviewLikeDAO.selectReviewLike(user_id, review_id);
  }

  public List<ReviewLike> selectReviewLikesByUserId(Long user_id) {
    return reviewLikeDAO.selectReviewLikesByUserId(user_id);
  }

  public ReviewLike getAllReviewLikesByReviewId(Long id) {
    return reviewLikeDAO.getAllReviewLikesByReviewId(id);
  }

  public int updateReviewLike(ReviewLike reviewLike) {
    return reviewLikeDAO.updateReviewLike(reviewLike);
  }

  public int deleteReviewLike(Long user_id, Long review_id) {
    return reviewLikeDAO.deleteReviewLike(user_id, review_id);
  }
}
