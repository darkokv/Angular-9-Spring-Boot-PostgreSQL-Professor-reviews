package com.example.professorreviews.api;

import com.example.professorreviews.model.AverageRates;
import com.example.professorreviews.model.Institute;
import com.example.professorreviews.model.Lecturer;
import com.example.professorreviews.model.Review;
import com.example.professorreviews.service.ReviewService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://192.168.8.100:4200"})
public class ReviewController {

  private final ReviewService reviewService;

  @Autowired
  public ReviewController(ReviewService reviewService) {
    this.reviewService = reviewService;
  }

  //Select review by id (GET)
  @GetMapping("/pr/review/{id}")
  @ResponseBody
  public Review selectReviewById(@PathVariable("id") Long id) {
    return reviewService.selectReviewById(id);
  }

  //Select review by id (GET)
  @GetMapping("/pr/reviewsbylecturer/{id}")
  @ResponseBody
  public List<Review> getAllReviewsByLecturerId(@PathVariable("id") Long id) {
    return reviewService.getAllReviewsByLecturerId(id);
  }

  //Get average rate by lecturer id (GET)
  @GetMapping("/pr/averagerate/{id}")
  @ResponseBody
  public Double getAverageRate(@PathVariable("id") Long id) {
    return reviewService.getAverageRate(id);
  }

  //Get average rates by lecturer id (GET)
  @GetMapping("/pr/averagerates/{id}")
  @ResponseBody
  public AverageRates getAverageRates(@PathVariable("id") Long id) {
    return reviewService.getAverageRates(id);
  }

  //Get average rates by institute id (GET)
  @GetMapping("/pr/averageratesbyinstitute/{id}")
  @ResponseBody
  public Double getAverageRatesByInstituteId(@PathVariable("id") Long id) {
    return reviewService.getAverageRateByInstituteId(id);
  }

  //Get number of reviews by institute id (GET)
  @GetMapping("/pr/numberofreviewsbyinstitute/{id}")
  @ResponseBody
  public Integer getNumberOfReviewsByInstituteId(@PathVariable("id") Long id) {
    return reviewService.getNumberOfReviewsByInstituteId(id);
  }

  @PostMapping("/pr/insertreview")
  public int insertReview(@RequestBody Review review) {
    return reviewService.insertReview(review);
  }

  //Update review by id (PUT)
  @PutMapping("/pr/updatereview/{id}")
  public Review updateReviewById(@PathVariable Long id, @RequestBody Review review)  {
    return reviewService.updateReviewById(id, review);
  }

  //Delete review by id (DELETE)
  @DeleteMapping("/pr/deletereview/{id}")
  public int deleteReviewById(@PathVariable Long id) {
    return reviewService.deleteReviewById(id);
  }


}
