import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiCommunicationService } from './ApiCommunication.service';

@Injectable()
export class UserService {

  constructor(private api: ApiCommunicationService) { }

  getUsers(): Observable<any> {
    return this.api.user().getUsers();
  }

  updateUser(id: number, user: any): Observable<any> {
    return this.api.user().updateUser(id, user);
  }
}
