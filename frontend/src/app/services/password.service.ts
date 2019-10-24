import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';


@Injectable({
  providedIn: 'root'
})
export class PasswordService {

  public Url = 'localhost:8080/user/changepassw';

  constructor(private http: HttpClient) { }

  sendPassword(firstPassword: string, secondPassword: string, id: number){
    return this.http.put(this.Url + id, {firstPassword, secondPassword});
  }
}
