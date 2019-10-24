import { Injectable } from "@angular/core";
import { Absence } from 'src/app/models/Absence.model';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
  })


export class AdminAbsenceResolver implements Resolve<Absence[]> {

    constructor(private api: ApiCommunicationService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Absence[] | Observable<Absence[]> | Promise<Absence[]> {
        return this.api.adminAbsence().getAbsences();
    }
}