import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { NgCircleProgressModule } from 'ng-circle-progress';
import { NgxChartsModule } from '@swimlane/ngx-charts';
import { AngularResizedEventModule } from 'angular-resize-event';
import { ToastrModule } from 'ngx-toastr';
//import { NgAnimatedCounterModule } from '@bugsplat/ng-animated-counter'
//import { FilePickerModule } from 'ngx-awesome-uploader';

import {NgbPaginationModule, NgbAlertModule} from '@ng-bootstrap/ng-bootstrap';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { TemplateComponent } from './template/template.component';
import { NavbarComponent } from './template/navbar/navbar.component';
import { HomeComponent } from './home/home.component';
import { FooterComponent } from './template/footer/footer.component';
import { ProfessorComponent } from './professor/professor.component';
import { ProfessorCardComponent } from './professor/professor-card/professor-card.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { ProfessorProfileComponent } from './professor/professor-profile/professor-profile.component';
import { InstitutesComponent } from './institutes/institutes.component';
import { CommentComponent } from './professor/professor-profile/comment/comment.component';
import { InstituteCardComponent } from './institutes/institute-card/institute-card.component';
import { LoginComponent } from './user/login/login.component';
import { RegisterComponent } from './user/register/register.component';
import { EditUserComponent } from './user/edit-user/edit-user.component';
import { ErrorComponent } from './error/error.component';
import { TopInstitutesComponent } from './home/top-institutes/top-institutes.component';
import { TopProfessorsComponent } from './home/top-professors/top-professors.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';

@NgModule({
  declarations: [
    AppComponent,
    TemplateComponent,
    NavbarComponent,
    HomeComponent,
    FooterComponent,
    ProfessorComponent,
    ProfessorCardComponent,
    ProfessorProfileComponent,
    InstitutesComponent,
    CommentComponent,
    InstituteCardComponent,
    LoginComponent,
    RegisterComponent,
    EditUserComponent,
    ErrorComponent,
    TopInstitutesComponent,
    TopProfessorsComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    //NgAnimatedCounterModule,
    NgxChartsModule,
    NgCircleProgressModule.forRoot({
      radius: 100,
      outerStrokeWidth: 16,
      innerStrokeWidth: 8,
      outerStrokeColor: "#78C000",
      innerStrokeColor: "#C7E596",
      animationDuration: 500
    }),
    AngularResizedEventModule,
    ToastrModule.forRoot(),
    NgbModule,
    NgbPaginationModule,
    NgbAlertModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
