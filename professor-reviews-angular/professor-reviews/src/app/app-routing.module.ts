import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ErrorComponent } from './error/error.component';
import { HomeComponent } from './home/home.component';
import { TopInstitutesComponent } from './home/top-institutes/top-institutes.component';
import { TopProfessorsComponent } from './home/top-professors/top-professors.component';
import { InstitutesComponent } from './institutes/institutes.component';
import { ProfessorProfileComponent } from './professor/professor-profile/professor-profile.component';
import { ProfessorComponent } from './professor/professor.component';
import { TemplateComponent } from './template/template.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';


const routes: Routes = [
  {
    path: '', component: TemplateComponent,
    children: [
      { path: '', component: HomeComponent },
      { path: 'institucije', component: InstitutesComponent },
      { path: 'profesori', component: ProfessorComponent },
      { path: 'profesor/:id', component: ProfessorProfileComponent },
      { path: 'prijava', component: LoginComponent },
      { path: 'registracija', component: RegisterComponent },
      { path: 'profil', component: EditUserComponent },
      { path: 'top-10-institucija', component: TopInstitutesComponent },
      { path: 'top-10-profesora', component: TopProfessorsComponent }
    ]
  },
  { 
    path: '**', component: TemplateComponent,
    children: [
      { path: '**', component: ErrorComponent}
      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
