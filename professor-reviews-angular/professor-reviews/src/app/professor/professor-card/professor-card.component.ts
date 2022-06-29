import { Component, Input, OnInit } from '@angular/core';
//import { NgAnimatedCounterModule } from '@bugsplat/ng-animated-counter'
import * as $ from 'jquery';

@Component({
  selector: 'app-professor-card',
  templateUrl: './professor-card.component.html',
  styleUrls: ['./professor-card.component.scss']
})
export class ProfessorCardComponent implements OnInit {
  @Input() id: number;
  @Input() coverPicture: string;
  @Input() profilePicture: string;
  @Input() fullname: string;
  @Input() institute: string;
  @Input() city: string;
  @Input() averageRate: number;
  @Input() email: string;
  @Input() ratesCount: number;

  rateValue: boolean = false;
  //public params: NgAnimatedCounterModule = { start: 0, end: 10, interval: 50 };
  
  constructor() { }

  ngOnInit(): void {
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
