import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../models/User';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private fb:FormBuilder, private http:HttpClient, private router: Router, private toastr: ToastrService) { }

  readonly BaseUrl = "http://192.168.8.100:8080/pr";
  
  private loginStatus = new BehaviorSubject<boolean>(this.checkLoginStatus());
  private fullname = new BehaviorSubject<string>(this.setFullname());

  //registration
  formModel = this.fb.group({
    fullname : ['', Validators.required],
    email : ['', [Validators.required, Validators.email]],
    username : ['', Validators.required],
    passwords: this.fb.group({
      password : ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword : ['', Validators.required]
    }, { validator : this.comparePasswords })
  })

  //updatePassword
  formModelUpdate = this.fb.group({
    oldPassword : ['', Validators.required],
    passwords: this.fb.group({
      newPassword : ['', [Validators.required, Validators.minLength(8)]],
      confirmNewPassword : ['', Validators.required]
    }, { validator : this.comparePasswordsUpdate })

  })

  comparePasswords(fg:FormGroup) {
    let confirmPswrdCtrl = fg.get('confirmPassword');
    if(confirmPswrdCtrl.errors == null || 'passwordMismatch' in confirmPswrdCtrl.errors) {
      if(fg.get('password').value != confirmPswrdCtrl.value)
        confirmPswrdCtrl.setErrors({passwordMismatch:true});
      else
        confirmPswrdCtrl.setErrors(null);
    }
  }

  comparePasswordsUpdate(fg:FormGroup) {
    let confirmPswrdCtrl = fg.get('confirmNewPassword');
    if(confirmPswrdCtrl.errors == null || 'passwordMismatch' in confirmPswrdCtrl.errors) {
      if(fg.get('newPassword').value != confirmPswrdCtrl.value)
        confirmPswrdCtrl.setErrors({passwordMismatch:true});
      else
        confirmPswrdCtrl.setErrors(null);
    }
  }

  register() {
    var data = {
      fullname: this.formModel.value.fullname,
      email: this.formModel.value.email,
      username: this.formModel.value.username,
      password: this.formModel.value.passwords.password,
      role: 'user'
    };
    return this.http.post(this.BaseUrl + '/insertuser', data);
  }

  login(credentials) {

    return this.http.post<any>(this.BaseUrl + '/user/login', credentials).subscribe(res => {
        if(res) {
          this.loginStatus.next(true);
          this.fullname.next(res.fullname);
          this.router.navigateByUrl("/");
          this.toastr.success("Uspešno ste se prijavili !" , "Dobro došli");
          sessionStorage.setItem("user", JSON.stringify(res));
          sessionStorage.setItem("role", JSON.stringify(res.role));

          //localStorage.setItem("user", JSON.stringify(res));
          //ocalStorage.setItem("role", JSON.stringify(res.status));
        }
        else {
          this.toastr.error("Pogrešno korisničko ime ili lozinka","Neuspešna prijava !");
        }
      },
      err => {
        console.log(err);
        if(err.error.message == "401") {
          this.toastr.error("Pogrešno korisničko ime ili lozinka","Neuspešna prijava !");
        }

      }
    
    );

  }

  logout() {
    this.loginStatus.next(false);
    this.toastr.success("Uspešno ste se odjavili !");
    sessionStorage.removeItem('user');
    sessionStorage.removeItem('role');
    this.router.navigate(['/']);
  }

  checkLoginStatus() : boolean {
    if(sessionStorage.getItem("user") != null) {
      return true;
    }
    else {
      return false;
    }
  }

  setFullname() {
    if(sessionStorage.getItem("user") != null) {
      var user = JSON.parse(sessionStorage.getItem("user"));
      return user.fullname;
    }
    else {
      return null;
    }
  }

  get isLoggedIn() {
    return this.loginStatus.asObservable();
  }

  get fullName() {
    return this.fullname.asObservable();
  }

  updatePassword(id: number) {
    var data = {
      oldpassword: this.formModelUpdate.value.oldPassword,
      newpassword: this.formModelUpdate.value.passwords.newPassword,
    };
    return this.http.put(this.BaseUrl + '/updatepassword/' + id, data);
  }

  /*
  roleMatch(allowedRole): boolean {
    let isMatch = false;
    let admin = JSON.parse(sessionStorage.getItem("role"));
    if(allowedRole == admin) {
      isMatch = true;
    }
    return isMatch;
  }
  */
}
