package com.example.professorreviews.dao;

import com.example.professorreviews.model.AverageRates;
import com.example.professorreviews.model.Review;
import java.util.List;
import java.util.Random;
import org.springframework.transaction.annotation.Transactional;

public interface ReviewDAO {

  @Transactional
  int insertReview(Long id, Review review);

  @Transactional
  default int insertReview(Review review) {
    Long id = new Random().nextLong();
    return insertReview(id, review);
  }

  @Transactional
  List<Review> getAllReviewsByLecturerId(Long id);

  @Transactional
  Review selectReviewById(Long id);

  @Transactional
  Double getAverageRate(Long id);

  @Transactional
  AverageRates getAverageRates(Long id);

  @Transactional
  Double getAverageRateByInstituteId(Long id);

  @Transactional
  Integer getNumberOfReviewsByInstituteId(Long id);

  @Transactional
  Review updateReviewById(Long id, Review review);

  @Transactional
  int deleteReviewByid(Long id);

}
