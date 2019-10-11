import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';
import { Router } from '@angular/router';
import { resolve } from 'q';
import { User } from '../models/User.model';

const URL = 'http://localhost:8080/';
const httpOptions = {
  headers: new HttpHeaders({
    'Authorization': 'Basic ' + btoa("fooClientIdPassword:secret"),
    'Content-type': 'application/x-www-form-urlencoded'
  })
};

/* Reasons for login rejection */
export enum LoginRejectionReason {
  ACCEPTED = "",
  UNKNOWN = "Unknown error",
  SERVICE_UNAVAILABLE = "Service currently unavailable",
  BAD_CREDENTIALS = "Invalid username or password"
}

@Injectable()

export class SessionService {

  private _userData$: BehaviorSubject<User> = new BehaviorSubject(undefined);
  private _isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject(this.hasToken());


  constructor(private http: HttpClient, private router: Router) { }


  public login(username, password): Promise<LoginRejectionReason> {

    const body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password');

    return new Promise<any>((resolve, reject) => {
      this.http.post(URL + 'oauth/token', body, httpOptions).subscribe((data: any) => {
        localStorage.setItem('token', data.access_token);
        this._isLoggedIn$.next(true);
        this.router.navigate(["admin/absence-index"]);
        resolve();
      }, (error: HttpErrorResponse) => {
        //Invalid credentials
        if (error.status === 401) {
          reject(LoginRejectionReason.BAD_CREDENTIALS);
        } else if (error.status === 0 || error.status == 503) {
          reject(LoginRejectionReason.SERVICE_UNAVAILABLE);
        } else {
          reject(LoginRejectionReason.UNKNOWN);
        }
      });
    });



    /* (data : any) => {
    localStorage.setItem('token', data.access_token);
    this._isLoggedIn$.next(true);
    this.router.navigate(["admin/absence-index"]);
});
}); */

  }

  public logout(): Promise<boolean> {
    return new Promise<boolean>(resolve => {
      localStorage.removeItem('token');
      this._isLoggedIn$.next(false);
      resolve(true);
    });
  }


  public getUserData(): User {
    return this._userData$.getValue();
  }

  public hasToken(): boolean {
    return !!localStorage.getItem('token');
  }

  public isLoggedIn(): Observable<boolean> {
    return this._isLoggedIn$;
  }

  private log(message: string) {
    console.log(message);
  }
}
