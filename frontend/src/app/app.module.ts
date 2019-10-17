import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule} from '@angular/material/menu';
import { NgModule } from '@angular/core';
import { MatPaginatorModule } from '@angular/material';

import {
  HttpClientModule,
  HttpInterceptor,
  HttpRequest,
  HttpHandler
} from "@angular/common/http";
import {
  MatToolbarModule,
  MatNativeDateModule,
  MatIconModule,
  MatSidenavModule,
  MatListModule,
  MatButtonModule,
  MatGridListModule,
  MatInputModule,
  MatCardModule,
  MatTableModule,
  MatFormFieldModule,
  MatDialogModule,
  MatDatepickerModule,
  MatDialogTitle,
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatSelectModule
} from "@angular/material/";

import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { TokenInterceptor } from "./token.interceptor";

//Own Components
import { AppComponent } from "./app.component";
import { HeaderComponent } from "./components/header/header.component";
import { FooterComponent } from "./components/footer/footer.component";
import { ContentComponent } from "./components/content/content.component";
import { AdminUserShowComponent } from "./components/admin/user-index/admin-user-index/admin-user-show.component";
import { FilterComponent } from "./components/filter/filter.component";
import { AdminAbsencesIndexComponent } from "./components/admin/absences-index/admin-absences-index.component";
import { AdminUserEditDestroyShowComponent } from "./components/admin/user-edit-destroy-show/admin-user-edit-destroy-show/admin-user-edit-destroy-show.component";
import { LoginComponent } from "./components/login/login.component";
import { SessionService } from "./services/session.service";
import { AbsencesCreateComponent } from "./components/employee/absences-create/absences-create.component";
import { ApiCommunicationService } from "./services/ApiCommunication.service";
import { UserService } from "./services/user.service";
import { CreateUserComponent } from './modals/create-user/create-user.component';
import { EmployeeService } from "./services/employee.service";
import { UserResolver } from "./UserResolver";
import { UserAbsenceIndexComponent } from "./components/employee/emp-absence-index/user-absence-index.component";


@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ContentComponent,
    AdminAbsencesIndexComponent,
    AdminUserEditDestroyShowComponent,  
    LoginComponent,
    AdminUserShowComponent,
    FilterComponent,
    CreateUserComponent,
    AbsencesCreateComponent,
    UserAbsenceIndexComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatGridListModule,
    MatCardModule,
    MatTableModule,
    MatInputModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatInputModule,
    MatMenuModule,
    MatFormFieldModule,
    MatDialogModule,
    MatPaginatorModule,
    MatDatepickerModule,
    MatSelectModule
  ],
  providers: [
    UserResolver,
    EmployeeService,
    SessionService,
    ApiCommunicationService,
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    { provide: MatDialogTitle, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
    { provide: MAT_DIALOG_DATA, useValue: [] }
   ],
  bootstrap: [AppComponent],
  entryComponents: [CreateUserComponent, AbsencesCreateComponent]
})
export class AppModule {}
