import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { AverageRates } from '../models/AverageRates';
import { Institute } from '../models/Institute';
import { InstituteCount } from '../models/InstituteCount';

@Injectable({
  providedIn: 'root'
})
export class InstituteService {


  constructor(private http:HttpClient) { }

  readonly BaseUrl = "http://192.168.8.100:8080/pr";
    
  getInstitutesByCityName(city: string): Observable<Institute[]> {
    return this.http.get<Institute[]>(this.BaseUrl + '/institutesbycity/' + city);
  }

  getInstitutesCount(): Observable<InstituteCount[]> {
    return this.http.get<InstituteCount[]>(this.BaseUrl + '/getallinstitutescount');
  }

  getAverageRatesByInstituteId(id: number): Observable<number> {
    return this.http.get<number>(this.BaseUrl + '/averageratesbyinstitute/' + id);
  }

  getNumberOfReviewsByInstituteId(id: number): Observable<number> {
    return this.http.get<number>(this.BaseUrl + '/numberofreviewsbyinstitute/' + id);
  }

  getTop10Institutes(): Observable<Institute[]> {
    return this.http.get<Institute[]>(this.BaseUrl + '/gettop10institutes');
  }
}
