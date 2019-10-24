import { Injectable } from '@angular/core';

import { Resolve } from '@angular/router';

import { Observable } from 'rxjs';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { Group } from './models/Group.model';
import { ApiCommunicationService } from './services/api-communication.service';




@Injectable()
export class GroupResolver implements Resolve<Group[]>{

    constructor(private api: ApiCommunicationService){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Group[] | Observable<Group[]> | Promise<Group[]> {
        return this.api.group().getGroups();
    }

}