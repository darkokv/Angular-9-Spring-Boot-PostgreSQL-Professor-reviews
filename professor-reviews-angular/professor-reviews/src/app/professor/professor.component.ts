import { ThrowStmt } from '@angular/compiler';
import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { features } from 'process';
import { Lecturer } from '../models/Lecturer';
import { LecturerService } from '../services/lecturer.service';

@Component({
  selector: 'app-professor',
  templateUrl: './professor.component.html',
  styleUrls: ['./professor.component.scss']
})
export class ProfessorComponent implements OnInit {

  allLecturers: Array<Lecturer>;
  searchLecturers: Array<Lecturer> = [];
  keyword: string;
  submitted: boolean = false;
  fetched: boolean = false;

  page: number = 1;
  pageSize: number = 12;

  constructor(private service: LecturerService, private router: Router, private activatedRoute: ActivatedRoute) {
    
    this.activatedRoute.queryParams.subscribe(params => {
      this.keyword = params.search;
      this.searchLecturer(this.keyword);
    })
  }

  myTimer() {
    this.submitted = true;
  }

  ngOnInit(): void {
    this.getLecturers();
  }

  onSubmit(keyword: string) {
    this.submitted = true;
    this.router.navigate(['/profesori'], { queryParams: { search: keyword } });
  }

  getLecturers() {
    this.service.getAllLecturers().subscribe(
      (res) => {
        this.allLecturers = res;
      },
      err => {
        console.log(err);
      }
    )
  }

  searchLecturer(keyword: string) {
    this.searchLecturers = [];
    this.service.searchLecturer(keyword).subscribe(
      (res) => {
        if(res) {
          res.forEach(l => {
            let lec = l;
  
            this.service.getAverageRate(lec.id).subscribe(
              (res) => {
                lec.averageRate = res;
              },
              err => {
                console.log(err);
              }
            );
  
            this.searchLecturers.push(lec);
          });
        }
        this.fetched = true;
      },
      err => {
        console.log(err);
      }
    )
  }

  goToTop() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
  }


}
