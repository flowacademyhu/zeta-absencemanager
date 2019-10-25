import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiCommunicationService } from './api-communication.service';

@Injectable()
export class UserService {

  constructor(private api: ApiCommunicationService) { }

  getUsers(): Observable<any> {
    return this.api.user().getUsers();
  }
  getUser(id: number): Observable<any> {
    return this.api.user().getUser(id);
  }

  deleteUser(id: number): Observable<any> {
    console.log("ID: " + id);
    return this.api.employee().deleteUser(id);
  }

}
