import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ApiCommunicationService } from './api-communication.service';
import { Observable, Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class PasswordService {

  private _unsubscribe$ = new Subject<void>();

  public Url = 'localhost:8080/user/changepassw/';

  constructor(private api: ApiCommunicationService) { }

  /* sendPassword(firstPassword: string, secondPassword: string, id: number){
    console.log("first:" + firstPassword, "second: " + secondPassword);
    console.log("Id: " + id);
    return this.http.put(this.Url + id, {dataA: firstPassword, dataB: secondPassword});
  } */

  sendPassword(firstPassword: string, secondPassword: string, id: number): Observable<any> {
    console.log("first:" + firstPassword, "second: " + secondPassword);
    console.log("Id: " + id);   
    return this.api.employee().changePassword(id, firstPassword, secondPassword);
  }


}
