import { Injectable } from "@angular/core";
import { Absence } from 'src/app/models/Absence.model';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { Observable } from 'rxjs';
import { PagedResponse } from '../models/PagedResponse.model';

@Injectable()
export class AdminAbsenceResolver implements Resolve<PagedResponse<Absence>> {

    constructor(private api: ApiCommunicationService) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): 
    PagedResponse<Absence> | Observable<PagedResponse<Absence>> | Promise<PagedResponse<Absence>> {
        //parameters
        const page = +route.queryParams['page'];
        const size = +route.queryParams['size'];
        
        return this.api.adminAbsence().getAbsences(size?size: 5, page?page: 0);
    }
}