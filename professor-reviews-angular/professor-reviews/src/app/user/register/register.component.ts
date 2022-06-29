import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import * as $ from 'jquery';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {

  constructor(public service: UserService, private router: Router, private toastr: ToastrService) { 
    if(sessionStorage.getItem('user') != null) {
      this.router.navigateByUrl('/');
    }
  }

  ngOnInit(): void {
    this.service.formModel.reset();
  }

  onSubmit() {
    this.service.register().subscribe(
      (res:any) => {
        if(res == 1) {
          this.service.formModel.reset();
          this.toastr.success('Novi korisnik kreiran !', 'Registracija uspesna !' );
          this.router.navigateByUrl("/prijava");
        }
        else {
          this.toastr.error("Registracija neuspesna !");
        }
      },
      err => {
        console.log(err);
        if(err.error.message == '409')
        this.toastr.error("Korisničko ime je već zauzeto","Registracija neuspešna !");
      }
    );
  }

}
