import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  public auth: boolean = false;

  formModel = {
    username: '',
    password: ''
  }

  constructor(private service: UserService, private router: Router, private toastr: ToastrService) {
    if(sessionStorage.getItem('user') != null) {
      this.router.navigateByUrl('/');
    }
   }

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
    this.service.login(form.value);
  }
  
}
