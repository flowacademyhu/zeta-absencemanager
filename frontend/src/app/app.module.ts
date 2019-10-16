import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatMenuModule} from '@angular/material/menu';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { HttpClientModule, HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { MatToolbarModule, MatNativeDateModule, MatIconModule, MatSidenavModule, MatListModule, MatButtonModule, MatGridListModule, MatInputModule, MatCardModule, MatTableModule, MatFormFieldModule } from '@angular/material/';


import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './token.interceptor';

//Own Components
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { ContentComponent } from './components/content/content.component';
import { AdminUserShowComponent } from './components/admin/user-index/admin-user-index/admin-user-show.component';
import { FilterComponent } from './components/filter/filter.component';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';
import { LoginComponent } from './components/login/login.component';
import { SessionService } from './services/session.service';
<<<<<<< HEAD
import { EmployeeShowComponent } from './components/employee/employee-show/employee-show.component';
import { EmployeeEditComponent } from './components/employee/employee-edit/employee-edit.component';
=======
import { ApiCommunicationService } from './services/ApiCommunication.service';
import { UserService } from './services/user.service';
>>>>>>> development

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ContentComponent,
    AbsencesIndexComponent,
    LoginComponent,
    AdminUserShowComponent,
    FilterComponent,
    EmployeeShowComponent,
    EmployeeEditComponent
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
    FlexLayoutModule,

  ],
  providers: [
    SessionService,
    ApiCommunicationService,
    UserService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
   ],
  bootstrap: [AppComponent]
})
export class AppModule { }
