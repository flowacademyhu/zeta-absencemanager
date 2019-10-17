import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Group } from '../models/Group.model';
import { HttpClient } from '@angular/common/http';
import { ApiCommunicationService } from '../services/ApiCommunication.service';

@Injectable({
  providedIn: 'root'
})

export class GroupResolver implements Resolve<Group[]> {

  constructor(private http: HttpClient, private api: ApiCommunicationService) { }

  resolve(route: import("@angular/router").ActivatedRouteSnapshot, state: import("@angular/router").RouterStateSnapshot): Group[] | import("rxjs").Observable<Group[]> | Promise<Group[]> {
    return this.api.group().getGroups();
  }
}
