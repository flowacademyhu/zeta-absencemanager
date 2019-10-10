import { AppRoutingModule } from './app-routing.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpInterceptor, HttpRequest, HttpHandler } from '@angular/common/http';
import { MatToolbarModule, MatIconModule, MatSidenavModule, MatListModule, MatButtonModule, MatGridListModule, MatInputModule, MatCardModule, MatTableModule } from '@angular/material/';

//Own Components
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { FooterComponent } from './components/footer/footer.component';
import { ContentComponent } from './components/content/content.component';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';
import { LoginComponent } from './components/login/login.component';
import { SessionService } from './services/session.service';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    FooterComponent,
    ContentComponent,
    AbsencesIndexComponent,
    LoginComponent
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
    HttpClientModule
  ],
  providers: [SessionService],
  bootstrap: [AppComponent]
})
export class AppModule { }
