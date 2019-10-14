import { HttpClient, HttpParams, HttpErrorResponse } from '@angular/common/http';
import { AbstractApiConnector, Connectors } from './AbstractApiConnector';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { User } from '../User.model';
import { LoginRejectionReason } from 'src/app/services/session.service';
import { Router } from '@angular/router';

@Injectable()
export class AuthConnector<T> extends AbstractApiConnector<T>{

    private _userData$: BehaviorSubject<User> = new BehaviorSubject(undefined);
    private _isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject(this.hasToken());

    constructor(protected _http: HttpClient, protected connector : Connectors, private router : Router){
        super(_http, Connectors.AUTH);
    }

    login(username: string, password: string) {

        const body = new HttpParams()
            .set('username', username)
            .set('password', password)
            .set('grant_type', 'password');

        return new Promise<any>((resolve, reject) => {  
            this._http.post(this.baseURL + 'oauth/token', body, this.httpOptions).subscribe((data : any) => {
              localStorage.setItem('token', data.access_token);
              this._isLoggedIn$.next(true);
              this.router.navigate(["admin/absence-index"]);
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
          resolve(true);
        });
      }

    public hasToken() : boolean {
        return !!localStorage.getItem('token');
    }
    
    public isLoggedIn() : Observable<boolean> {
      return this._isLoggedIn$;
    }


}