import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router, UrlTree } from '@angular/router';
import { Injectable } from '@angular/core';
import { SessionService } from '../services/session.service';
import { Observable } from 'rxjs';

@Injectable()
export class AdminGuard implements CanActivate {

    constructor(private session: SessionService, private router: Router) {}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        const isAdmin = this.session.getUserData().role === 'ADMIN';
        //TODO leader can access admin-group path yet
        const isLeader = this.session.getUserData().role === 'LEADER';
        if(!isAdmin && !isLeader) {
            this.router.navigate(["absences"]);
        } else {
            return true;
        }

    }

}