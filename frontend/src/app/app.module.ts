import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';

import { HttpClientModule, HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { MatToolbarModule, MatNativeDateModule, MatIconModule, MatSidenavModule, MatListModule, MatButtonModule, MatGridListModule, MatInputModule, MatCardModule, MatTableModule, MatFormFieldModule, MatDialogModule } from '@angular/material/';


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
import { AbsenceCreateComponent } from './components/admin/absence-create/absence-create.component';
import { LoginComponent } from './components/login/login.component';
import { SessionService } from './services/session.service';
import { AdminAbsenceCreateComponent } from './modals/admin-absence-create/admin-absence-create.component';

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
    AbsencesIndexComponent,
    AbsenceCreateComponent,
    AdminAbsenceCreateComponent
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
    MatFormFieldModule,
    MatDialogModule

  ],
  providers: [
    SessionService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent],
  entryComponents: [AdminAbsenceCreateComponent]
})
export class AppModule { }
