import { Component, Input, OnInit } from '@angular/core';
import { AverageRates } from 'src/app/models/AverageRates';
import { InstituteService } from 'src/app/services/institute.service';
import { LecturerService } from 'src/app/services/lecturer.service';

@Component({
  selector: 'app-institute-card',
  templateUrl: './institute-card.component.html',
  styleUrls: ['./institute-card.component.scss']
})
export class InstituteCardComponent implements OnInit {
  @Input() id: number;
  @Input() coverPicture: string;
  @Input() name: string;
  @Input() adress: string;
  @Input() city: string;
  @Input() averageRate: number;
  @Input() avgPrint: number;
  @Input() numberOfReviews: number = 0;
  

  constructor(private service: InstituteService) { }

  ngOnInit(): void {
    this.getAverageRatesByInstituteId(this.averageRate);
    this.getNumberOfReviewsByInstituteId(this.averageRate);
  }

  getAverageRatesByInstituteId(id: number) {
    this.service.getAverageRatesByInstituteId(id).subscribe(
      (res) => {
        if(!isNaN(res)){
          this.avgPrint = res;
        }
      },
      err => {
        console.log(err);
      }
    )
  }

  getNumberOfReviewsByInstituteId(id: number) {
    this.service.getNumberOfReviewsByInstituteId(id).subscribe(
      (res) => {
        if(!isNaN(res)){
          this.numberOfReviews = res;
        }
      },
      err => {
        console.log(err);
      }
    )
  }

  rateCountPrint(number: number) {
    var lastone = +number.toString().split('').pop();
    if(lastone == 2 || lastone == 3 || lastone == 4)
    {
      if(number == 12 || number == 13 || number == 14) {
        return true;
      }
      else {
        return false;
      }

    }
    else {
      return true;
    }
  }



}
