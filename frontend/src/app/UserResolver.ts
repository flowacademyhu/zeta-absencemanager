import { Injectable } from '@angular/core';

import { Resolve } from '@angular/router';

import { Observable } from 'rxjs';
import { ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { User } from './models/User.model';
import { ApiCommunicationService } from './services/ApiCommunication.service';



@Injectable()
export class BasicResolver implements Resolve<User[]>{

    constructor(private api: ApiCommunicationService){}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): User[] | Observable<User[]> | Promise<User[]> {
        return this.api.user().getUsers();
    }
    
        
   /*  resolve(){
        return this.resolverTryService.getData();
    } */

    //resolve(route: import("@angular/router").ActivatedRouteSnapshot, state: import("@angular/router").RouterStateSnapshot) {
    //    throw new Error("Method not implemented.");"allowJs": true
    //}

}