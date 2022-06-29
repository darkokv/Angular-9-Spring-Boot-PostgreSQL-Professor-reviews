import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Lecturer } from '../models/Lecturer';
import { Observable } from 'rxjs';
import { AverageRates } from '../models/AverageRates';
import { Review } from '../models/Review';
import { Institute } from '../models/Institute';

@Injectable({
  providedIn: 'root'
})
export class LecturerService {

  constructor(private http:HttpClient) { }

  readonly BaseUrl = "http://192.168.8.100:8080/pr";
  
  getAllLecturers(): Observable<Lecturer[]> {
    return this.http.get<Lecturer[]>(this.BaseUrl + '/getalllecturers');
  }

  searchLecturer(keyword: string): Observable<Lecturer[]> {
    return this.http.get<Lecturer[]>(this.BaseUrl + '/searchlecturer/' + keyword);
  }

  getAverageRate(id: number): Observable<number> {
    return this.http.get<number>(this.BaseUrl + '/averagerate/' + id);
  }

  getAverageRates(id: number): Observable<AverageRates> {
    return this.http.get<AverageRates>(this.BaseUrl + '/averagerates/' + id);
  }

  getLecturerWithReviews(id: number): Observable<Lecturer> {
    return this.http.get<Lecturer>(this.BaseUrl + '/lecturerwithreviews/' + id);
  }

  getReviewLikesByLecturerId(id: number): Observable<Review> {
    return this.http.get<Review>(this.BaseUrl + '/reviewlikes/' + id);
  }

  getTop10Professors(): Observable<Lecturer[]> {
    return this.http.get<Lecturer[]>(this.BaseUrl + '/gettop10lecturers');
  }

  
}
