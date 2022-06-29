import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import * as $ from 'jquery';
import { Observable } from 'rxjs';
import { User } from 'src/app/models/User';
import { UserService } from 'src/app/services/user.service';
import { LoginComponent } from 'src/app/user/login/login.component';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {

  user = JSON.parse(sessionStorage.getItem("user"));
  loginStatus$: Observable<boolean>;
  fullname$: Observable<string>;


  constructor(private service: UserService,private router: Router) { }

  ngOnInit(): void {
    this.loginStatus$ = this.service.isLoggedIn;
    this.fullname$ = this.service.fullName;
  }

  closeNav() {
    if($(window).width() < 768) {
      $(".navbar-toggler-icon").click();
    }
  }

  onLogout() {
    this.service.logout();
  }




  




}
