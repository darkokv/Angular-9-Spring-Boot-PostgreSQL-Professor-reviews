package com.example.professorreviews.service;

import com.example.professorreviews.dao.ReviewDAO;
import com.example.professorreviews.model.AverageRates;
import com.example.professorreviews.model.Review;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  private final ReviewDAO reviewDAO;

  @Autowired
  public ReviewService(ReviewDAO reviewDAO) {
    this.reviewDAO = reviewDAO;
  }

  public int insertReview(Review review) {
    return reviewDAO.insertReview(review);
  }

  public Review selectReviewById(Long id) {
    return reviewDAO.selectReviewById(id);
  }

  public List<Review> getAllReviewsByLecturerId(Long id) {
    return reviewDAO.getAllReviewsByLecturerId(id);
  }

  public Double getAverageRate(Long id) {
    return reviewDAO.getAverageRate(id);
  }

  public AverageRates getAverageRates(Long id) {
    return reviewDAO.getAverageRates(id);
  }

  public Double getAverageRateByInstituteId(Long id) {
    return reviewDAO.getAverageRateByInstituteId(id);
  }

  public Integer getNumberOfReviewsByInstituteId(Long id) {
    return reviewDAO.getNumberOfReviewsByInstituteId(id);
  }

  public Review updateReviewById(Long id, Review review) {
    return reviewDAO.updateReviewById(id, review);
  }

  public int deleteReviewById(Long id) {
    return reviewDAO.deleteReviewByid(id);
  }
}
