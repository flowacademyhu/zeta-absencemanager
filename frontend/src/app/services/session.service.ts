import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { map } from 'rxjs/operators';

const URL = 'http://localhost:8080/';
const httpOptions = {
  headers: new HttpHeaders({
    'Authorization': 'Basic ' + btoa("fooClientIdPassword:secret"),
    'Content-type': 'application/x-www-form-urlencoded'
  })
};

@Injectable()

export class SessionService {

  private _userData$: BehaviorSubject<any> = new BehaviorSubject(undefined);
  private _isLoggedIn$: BehaviorSubject<boolean> = new BehaviorSubject(this.hasToken());
  private body : HttpParams;

  
  constructor(private http : HttpClient) { }


  public login(username, password){

    this.body = new HttpParams()
      .set('username', username)
      .set('password', password)
      .set('grant_type', 'password');
    
    this.http.post(URL + 'oauth/token', this.body, httpOptions).subscribe((data : any) => {
      localStorage.setItem('token', data.access_token);
      this._isLoggedIn$.next(true);
    });

  }

  public logout(){
    localStorage.removeItem('token');
    this._isLoggedIn$.next(false);
  }

  public get currentUserData(): BehaviorSubject<any> {
    return this._userData$.value;
}

  private hasToken() : boolean {
    return !!localStorage.getItem('token');
  }

  public isLoggedIn() : Observable<boolean> {
    return this._isLoggedIn$.asObservable();
  }

  private log(message: string) {
    console.log(message);
  }
}