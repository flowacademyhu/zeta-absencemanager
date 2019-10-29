import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Group } from '../models/Group.model';
import { HttpClient } from '@angular/common/http';
import { ApiCommunicationService } from '../services/api-communication.service';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})

export class GroupResolver implements Resolve<Group[]> {

  constructor(private http: HttpClient, private api: ApiCommunicationService) { }

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Group[] | Observable<Group[]> | Promise<Group[]> {
    return this.api.group().getGroups();
  }
}
