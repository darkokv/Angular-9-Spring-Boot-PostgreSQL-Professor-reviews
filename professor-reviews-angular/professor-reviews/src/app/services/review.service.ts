import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Review } from '../models/Review';

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  constructor(private http:HttpClient) { }

  readonly BaseUrl = "http://192.168.8.100:8080/pr";
  
  insertReview(review) {
    return this.http.post(this.BaseUrl + '/insertreview', review);
  }

  insertReviewLike(reviewLike) {
    return this.http.post(this.BaseUrl + '/insertreviewlike', reviewLike);
  }

  getAllReviewLikes(reviewId) {
    return this.http.get(this.BaseUrl + '/reviewlikes/' + reviewId);
  }

  selectReviewLike(userId, reviewId) {
    return this.http.get(this.BaseUrl + '/reviewlike/' + userId + '/' + reviewId);
  }

  selectReviewLikesByUserId(userId) {
    return this.http.get(this.BaseUrl + '/reviewlikebyuser/' + userId);
  }

  updateReviewLike(reviewUpdate) {
    return this.http.put(this.BaseUrl + '/updatereviewlike', reviewUpdate);
  }

  deleteReviewLike(userId, reviewId) {
    return this.http.delete(this.BaseUrl + '/deletereviewlike/' + userId + '/' + reviewId);
  }

  deleteReview(id: number) {
    return this.http.delete(this.BaseUrl + '/deletereview/' + id);
  }
  

}
