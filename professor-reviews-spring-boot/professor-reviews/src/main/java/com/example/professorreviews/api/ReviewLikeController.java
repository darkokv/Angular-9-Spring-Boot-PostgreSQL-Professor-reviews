package com.example.professorreviews.api;

import com.example.professorreviews.model.Review;
import com.example.professorreviews.model.ReviewLike;
import com.example.professorreviews.model.User;
import com.example.professorreviews.service.ReviewLikeService;
import java.util.List;
import javax.ws.rs.Path;
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
public class ReviewLikeController {

  private final ReviewLikeService reviewLikeService;

  @Autowired
  public ReviewLikeController(
      ReviewLikeService reviewLikeService) {
    this.reviewLikeService = reviewLikeService;
  }

  //Select review likes by id (GET)
  @GetMapping("/pr/reviewlikes/{id}")
  @ResponseBody
  public ReviewLike getAllReviewLikesByReviewId(@PathVariable("id") Long id) {
    return reviewLikeService.getAllReviewLikesByReviewId(id);
  }

  //Select review like by user id and review id
  @GetMapping("/pr/reviewlike/{user_id}/{review_id}")
  @ResponseBody
  public ReviewLike selectReviewLike(@PathVariable("user_id") Long user_id, @PathVariable("review_id") Long review_id) {
    return reviewLikeService.selectReviewLike(user_id, review_id);
  }

    //Select review likes by user id
    @GetMapping("/pr/reviewlikebyuser/{user_id}")
    @ResponseBody
    public List<ReviewLike> selectReviewLikesByUserId(@PathVariable("user_id") Long user_id) {
      return reviewLikeService.selectReviewLikesByUserId(user_id);
    }

  //Insert review like (POST)
  @PostMapping("/pr/insertreviewlike")
  public int insertReviewLike(@RequestBody ReviewLike reviewLike) {
    return reviewLikeService.insertReviewLike(reviewLike);
  }

  //Update review like by user id and review id (PUT)
  @PutMapping("/pr/updatereviewlike")
  public int updateReviewLike(@RequestBody ReviewLike reviewLike) {
    return reviewLikeService.updateReviewLike(reviewLike);
  }

  //Delete review like by user id and review id (DELETE)
  @DeleteMapping("/pr/deletereviewlike/{user_id}/{review_id}")
  public int deleteUserById(@PathVariable Long user_id, @PathVariable Long review_id) {
    return reviewLikeService.deleteReviewLike(user_id, review_id);
  }


}
