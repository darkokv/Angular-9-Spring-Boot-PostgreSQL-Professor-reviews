package com.example.professorreviews.dao;

import com.example.professorreviews.model.ReviewLike;
import java.util.List;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewLikeDAO {

  @Transactional
  int insertReviewLike(ReviewLike reviewLike);

  @Transactional
  ReviewLike getAllReviewLikesByReviewId(Long id);

  @Transactional
  ReviewLike selectReviewLike(Long user_id, Long review_id);

  @Transactional
  List<ReviewLike> selectReviewLikesByUserId(Long user_id);

  @Transactional
  int updateReviewLike(ReviewLike reviewLike);

  @Transactional
  int deleteReviewLike(Long user_id, Long review_id);

}
