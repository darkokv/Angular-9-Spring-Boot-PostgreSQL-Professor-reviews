import { Injectable } from '@angular/core';
import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return true;
  }

  /*
    constructor(private router: Router, private toastr: ToastrService, private userService: UserService) {

  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): boolean  {
      if(sessionStorage.getItem('user') != null) {
        let role = next.data["role"] as String;
        if(role) {
          let match = this.userService.roleMatch(role);
          if(match) return true;
          else {
            this.router.navigate(['/']);
            this.toastr.warning("Nemate dozvolu za pristupanje ovog mrežnog resursa", "Upozorenje!");
            return false;
          }
        }
        else {
          return true;
        }
      }
      else {
        this.router.navigate(['/login']);
        this.toastr.warning("Morate se prijaviti da biste pristupili mrežnom resursu", "Upozorenje!");
        return false;
      }
  }
  */
  
}
