import { Injectable, Injector } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { resolve } from 'q';
import { User } from '../models/User.model';
import { ApiCommunicationService } from './api-communication.service';


/* Reasons for login rejection */ 
export enum LoginRejectionReason {
  ACCEPTED = "",
	UNKNOWN = "Ismeretlen hiba.",
	SERVICE_UNAVAILABLE = "Szolgáltatás jelenleg nem elérhető.",
	BAD_CREDENTIALS = "Hibás felhasználónév vagy jelszó."
}

@Injectable()

export class SessionService {

  private _userData$: BehaviorSubject<User> = new BehaviorSubject(undefined);
  private _isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject(this.hasToken());
  private get router() { return this._injector.get(Router); }
  
  constructor(private api: ApiCommunicationService, private _injector: Injector) { }


  public login(username, password):  Promise<LoginRejectionReason> {

    return new Promise<any>((resolve, reject) => {  
      this.api.auth().getToken(username, password).subscribe((data : any) => {
        localStorage.setItem('token', data.access_token);
        this._isLoggedIn$.next(true);
        this.api.employee().getCurrent().subscribe(d => this._userData$.next(d));
        this.router.navigate(["absences"]);
        resolve();
      }, (error: HttpErrorResponse) => {
        //Invalid credentials
        if (error.status === 401 || error.status === 400) {
          reject(LoginRejectionReason.BAD_CREDENTIALS);
        } else if (error.status === 0 || error.status == 503) {
          reject(LoginRejectionReason.SERVICE_UNAVAILABLE);
        } else {
          reject(LoginRejectionReason.UNKNOWN);
        }
      });
    });
  }

  public logout(): Promise<boolean> {
    return new Promise<boolean>(resolve => {
      localStorage.removeItem('token');
      this._isLoggedIn$.next(false);
      this.router.navigate(["login"]);
      resolve(true);
    });
  }

  get userData(){
    return this._userData$;
  }

  public getUserDataWithObservable(): Observable<User> {
    return this._userData$;
  }

  public getUserData(): User {
    return this._userData$.getValue();
  }

  public hasToken() : boolean {
    return !!localStorage.getItem('token');
  }

  public isLoggedIn() : Observable<boolean> {
    return this._isLoggedIn$;
  }

  public isLoggedInValue(): boolean {
    return this._isLoggedIn$.getValue();
  }
  
  public startSessionOnApplicationBootstrap(): Promise<void> {
    return new Promise<void>(resolve => {
      this.api.employee().getCurrent().subscribe(user => {
        this._userData$.next(user);
        resolve();
      },
      error => { 
        localStorage.removeItem('token');
        this.router.navigate(["login"]);
        resolve();
      });
    });
  }

}
