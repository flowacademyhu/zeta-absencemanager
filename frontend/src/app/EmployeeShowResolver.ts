import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { User } from './models/User.model';
import { ApiCommunicationService } from './services/ApiCommunication.service';


@Injectable()
export class EmployeeShowResolver implements Resolve<User>{

    constructor(private api: ApiCommunicationService){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): User | Observable<User> | Promise<User> {
        return this.api.user().getUser(1);
    }
}