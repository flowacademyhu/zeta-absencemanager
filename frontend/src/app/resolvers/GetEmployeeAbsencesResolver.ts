import { Injectable } from "@angular/core";
import { Resolve } from "@angular/router";
import { Observable } from "rxjs";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Absence } from "../models/Absence.model";
import { ApiCommunicationService } from "../services/api-communication.service";
import { PagedResponse } from '../models/PagedResponse.model';

@Injectable()
export class GetEmployeeAbsencesResolver implements Resolve<PagedResponse<Absence>> {
  constructor(private api: ApiCommunicationService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): PagedResponse<Absence> | Observable<PagedResponse<Absence>> | Promise<PagedResponse<Absence>>  {
    const page = +route.queryParams['page'];
    const size = +route.queryParams['size'];
    return this.api.absence().getAbsences(size?size: 5, page?page: 0);
  }
}
