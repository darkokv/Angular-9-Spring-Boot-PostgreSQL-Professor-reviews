import { Component, OnInit } from '@angular/core';
import { Lecturer } from 'src/app/models/Lecturer';
import { LecturerService } from 'src/app/services/lecturer.service';

@Component({
  selector: 'app-top-professors',
  templateUrl: './top-professors.component.html',
  styleUrls: ['./top-professors.component.scss']
})
export class TopProfessorsComponent implements OnInit {

  topProfessors: Array<Lecturer> = [];

  constructor(private service: LecturerService) { }

  ngOnInit(): void {
    this.getTop10Professors();
  }

  getTop10Professors() {
    this.service.getTop10Professors().subscribe(
      (res) => {
        if(res){
          this.topProfessors = res;
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
    return this.topProfessors.sort((a, b) => a[prop] < b[prop] ? 1 : a[prop] === b[prop] ? 0 : -1);
  }

}
