import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AverageRates } from 'src/app/models/AverageRates';
import { Lecturer } from 'src/app/models/Lecturer';
import { LecturerService } from 'src/app/services/lecturer.service';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import * as shape from 'd3-shape';
import { ResizedEvent } from 'angular-resize-event';
import { Review } from 'src/app/models/Review';
import { NgForm } from '@angular/forms';
import * as $ from 'jquery';
import { ReviewService } from 'src/app/services/review.service';
import { ToastrService } from 'ngx-toastr';


@Component({
  selector: 'app-professor-profile',
  templateUrl: './professor-profile.component.html',
  styleUrls: ['./professor-profile.component.scss']
})
export class ProfessorProfileComponent implements OnInit {

  user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : 0;
  
  lecturer: Lecturer;
  lecturerId: number;
  averageRates: AverageRates;
  averageRateChart:Array<Object> = [];
  averageRate: number;

  //reviewLikesByLoggedUser;

  //chart properties
  view: any[] = [450, 300];
  // options
  legend: boolean = false;
  showLabels: boolean = true;
  animations: boolean = true;
  xAxis: boolean = true;
  yAxis: boolean = true;
  yAxisMinScale: number = 10;
  linearCurve = shape.curveLinearClosed;

  colorScheme = {
    domain: ['#269d98']
  };

  //leave comment
  formModel = {
    user_id: this.user.id,
    lecturer_id : null,
    useful_l: '',
    interesting_l: '',
    material: '',
    commitment: '',
    communication: '',
    comment: '',
    date: this.formatDate()
  }


  constructor(private service: LecturerService, private reviewService: ReviewService, private route: ActivatedRoute, private toastr: ToastrService) { }

  ngOnInit(): void {
    //this.rateLimit();
    
    this.route.paramMap
      .subscribe(params => {
        this.lecturerId  =+ params.get('id');
        this.formModel.lecturer_id = this.lecturerId;
      });

    this.getLecturerWithReviews(this.lecturerId);  
    this.getAverageRates(this.lecturerId);
    //if(this.user) {
    //  this.selectReviewLikesByUserId(this.user.id);
    //}

    
/*
      $('input[type="number"]').on('keyup change',function(){
          var v = parseInt($(this).val());
          var  min = parseInt($(this).attr('min'));
          var max = parseInt($(this).attr('max'));
  
          if (v < min){
              $(this).val(min);
          } 
          else if (v > max){
              $(this).val(max);
          }
      })
  */

  }

  limitRate($event) {
    if($event.target.value > 10) {
      $event.target.value = '';
    }
    else if($event.target.value < 1) {
      $event.target.value = '';
    }
    
  }

  refreshReviews() {
    setTimeout(()=>{                   
      this.getLecturerWithReviews(this.lecturerId);
    }, 100);

  }

  formatDate() {
    let now = new Date();
    let month = now.getMonth()+1;
    let date = now.getDate();
    let year = now.getFullYear();
    let parsedDate, parsedMonth, parsedYear;
    if(month < 10) {
      parsedMonth = "0" + month.toString(); 
    }
    else {
      parsedMonth = month.toString();
    } 

    if(date < 10) {
      parsedDate = "0" + date.toString(); 
    }
    else {
      parsedDate = date.toString();
    } 

    parsedYear = year.toString();

    let currentDate = parsedDate + "/" + parsedMonth + "/" + parsedYear;
    return currentDate;
  }

  getLecturerWithReviews(id: number) {
    this.service.getLecturerWithReviews(id).subscribe(
      (res) => {
        this.lecturer = res;
      },
      err => {
        console.log(err);
      }
    )
  }

  getAverageRates(id: number) {
    this.service.getAverageRates(id).subscribe(
      (res) => {
        this.averageRates = res;
        this.getAverageRate();
        this.setAverageRateChartArary(this.averageRates);
      },
      err => {
        console.log(err);
      }
    )
  }
  
  getAverageRate() {
    let sum = this.averageRates.useful_l + 
              this.averageRates.interesting_l + 
              this.averageRates.material + 
              this.averageRates.commitment + 
              this.averageRates.communication;

    this.averageRate = sum / 5;
    this.averageRate = Math.round((this.averageRate + Number.EPSILON) * 10) / 10;
  }

  setAverageRateChartArary(avgRate: AverageRates) {
    this.averageRateChart = [];
    var object = {
      "name": "",
      "series": [
        {
          "value": avgRate.useful_l,
          "name": "Korisna predavanja"
        },
        {
          "value": avgRate.interesting_l,
          "name": "Zanimljiva predavanja"
        },
        {
          "value": avgRate.material,
          "name": "Materijal za učenje"
        },
        {
          "value": avgRate.commitment,
          "name": "Zalaganje za predmet"
        },
        {
          "value": avgRate.communication,
          "name": "Komunikacija"
        },
      ]
    }
    this.averageRateChart.push(object);

  }

  onResize(event: ResizedEvent) {
    if(window.innerWidth < 576) {
      this.view = [event.newWidth, 300];
    }
    if(window.innerWidth < 500) {
      this.view = [event.newWidth, 300];
      //this.xAxis = false;
      this.yAxis = false;
    }
    /*
    else {
      this.view = [event.newWidth / 2.2, 300];
    }*/
  }

  rateLimit() {
    $(document).ready(function(){
      $('input[type="number"]').on('keyup',function(){
          console.log("radi");
          var v = parseInt($(this).val());
          var min = parseInt($(this).attr('min'));
          var max = parseInt($(this).attr('max'));
  
          if (v < min){
              $(this).val(min);
          } 
          else if (v > max){
              $(this).val(max);
          }
      })
    })
  }

  onSubmit(form: NgForm) {
    if(this.user) {
      this.reviewService.insertReview(this.formModel).subscribe(
        (res) => {
          if(res == 1) {
            this.toastr.success("Uspešno ste ocenili profesora !"); 
            this.getAverageRates(this.lecturerId);
            this.getLecturerWithReviews(this.lecturerId);
            form.reset();
          }
          else {
            this.toastr.error("Nešto nije u redu, pokušajte kasnije.");
          }
          
        },
        err => {
          console.log(err);
          if(err.error.message == '409') {
            this.toastr.error("Već ste ocenili ovog profesora !");
          }

        })
    }
    else {
      this.toastr.warning("Morate da se prijavite da biste ocenili profesora.");
    }
  }

  /*
  selectReviewLikesByUserId(userId) {
    this.reviewService.selectReviewLikesByUserId(userId).subscribe(
      (res) => {
        this.reviewLikesByLoggedUser = res;
      },
      err => {
        console.log(err);
      }
    )
  }


  chechIfUserVoted(reviewId) {
    if(this.reviewLikesByLoggedUser) {
      this.reviewLikesByLoggedUser.forEach(element => {
        if(element.review_id == reviewId) {
          return true;
        }
        return false;
      });
    }
    else {
      return false;
    }
  }
  */

}
