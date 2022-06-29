import { Component, OnInit } from '@angular/core';
import { Institute } from 'src/app/models/Institute';
import { InstituteService } from 'src/app/services/institute.service';

@Component({
  selector: 'app-top-institutes',
  templateUrl: './top-institutes.component.html',
  styleUrls: ['./top-institutes.component.scss']
})
export class TopInstitutesComponent implements OnInit {

  topInstitutes: Array<Institute> = [];

  constructor(private service: InstituteService) { }

  ngOnInit(): void {
    this.getTop10Institutes();
  }

  getTop10Institutes() {
    this.service.getTop10Institutes().subscribe(
      (res) => {
        if(res){
          this.topInstitutes = res;
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
      return false;
    }
    else {
      return true;
    }
  }

  sortBy(prop: string) {
    return this.topInstitutes.sort((a, b) => a[prop] < b[prop] ? 1 : a[prop] === b[prop] ? 0 : -1);
  }

}
