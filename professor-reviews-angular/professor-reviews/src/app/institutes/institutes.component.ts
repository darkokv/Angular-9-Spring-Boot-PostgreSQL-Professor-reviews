import { Component, OnInit } from '@angular/core';
import * as $ from 'jquery';
import { AverageRates } from '../models/AverageRates';
import { Institute } from '../models/Institute';
import { InstituteCount } from '../models/InstituteCount';
import { InstituteService } from '../services/institute.service';
import { LecturerService } from '../services/lecturer.service';

@Component({
  selector: 'app-institutes',
  templateUrl: './institutes.component.html',
  styleUrls: ['./institutes.component.scss']
})
export class InstitutesComponent implements OnInit {

  institutes: Array<Institute> = [];
  institutesCount: Array<InstituteCount> = [];
  averageRate: number;

  constructor(private service: InstituteService) { }

  ngOnInit(): void {
    this.getInstitutesCount();
    this.filterCities();
  }

  filterCities() {
    $(document).ready(function(){
      $("#myInput").on("keyup", function() {
        var value = $(this).val().toLowerCase();
        $("#list-tab a").filter(function() {
          console.log($(this).text().toLowerCase().indexOf(value));
          $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
        });
      });
    });
  }

  getInstitutesByCityName(city: string) {
    if(window.screen.width > 576) {
      window.scrollTo(0, 0);
    }
    this.service.getInstitutesByCityName(city).subscribe(
      (res) => {
        this.institutes = res;
      },
      err => {
        console.log(err);
      }
    )
  }

  getInstitutesCount() {
    this.service.getInstitutesCount().subscribe(
      (res) => {
        this.institutesCount = res;
      },
      err => {
        console.log(err);
      }
    )
  }

  getCountByCity(city: string) {
    if(this.institutesCount.length > 0) {
      var insc = this.institutesCount.find(element => element.city == city);
      if(insc){
        return insc.count;
      }
      return 0;
    }
  }

}
/*

this.averageRateInstitute.forEach(review => {
  var sum =  review.useful_l + review.interesting_l + review.material + review.commitment + review.communication;
  sum = sum / 5;
  console.log(sum)
});*/