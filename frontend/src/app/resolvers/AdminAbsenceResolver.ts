import { Injectable } from "@angular/core";
import { Absence } from "src/app/models/Absence.model";
import {
  Resolve,
  ActivatedRouteSnapshot,
  RouterStateSnapshot
} from "@angular/router";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { Observable } from "rxjs";
import { PagedResponse } from "../models/PagedResponse.model";
import { AbsencesPagedRequest } from "../models/AbsencesPagedRequest";

@Injectable()
export class AdminAbsenceResolver implements Resolve<PagedResponse<Absence>> {
  constructor(private api: ApiCommunicationService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | PagedResponse<Absence>
    | Observable<PagedResponse<Absence>>
    | Promise<PagedResponse<Absence>> {
    //parameters
    const page = +route.queryParams["page"];
    const size = +route.queryParams["size"];
    const administrationID = route.queryParams["administrationID"];
    const type = route.queryParams["type"];
    const status = route.queryParams["status"];
    const reporter = route.queryParams["reporter"];
    const assignee = route.queryParams["assignee"];
    const start = route.queryParams["start"];
    const finish = route.queryParams["finish"];
    const dayStart = route.queryParams["dayStart"];
    const dayEnd = route.queryParams["dayEnd"];

    const pagedRequest = new AbsencesPagedRequest(
      page ? page : 0,
      size ? size : 5,
      administrationID,
      type,
      status,
      reporter,
      assignee,
      start,
      finish,
      dayStart,
      dayEnd
    );

    return this.api.adminAbsence().getAbsences(pagedRequest);
  }
}
