import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ToastrService } from 'ngx-toastr';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit-user',
  templateUrl: './edit-user.component.html',
  styleUrls: ['./edit-user.component.scss']
})
export class EditUserComponent implements OnInit {

  user = sessionStorage.getItem("user") ? JSON.parse(sessionStorage.getItem("user")) : 0;

  constructor(public service: UserService, private router: Router, private toastr: ToastrService) { }

  ngOnInit(): void {
  }

  onSubmit() {
    this.service.updatePassword(this.user.id).subscribe(
      (res:any) => {
        if(res == 1) {
          this.toastr.success('Promena lozinke uspešna !');
          this.router.navigateByUrl("/");
        }
        else {
          this.toastr.error("Promena lozinke neuspešna !");
        }
      },
      err => {
        console.log(err);
        if(err.error.message == '400')
        this.toastr.error("Stara lozinka nije validna.","Promena lozinke neuspešna!");
      }
    );
  }

}
