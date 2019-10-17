import { Injectable } from "@angular/core";
import { Resolve } from "@angular/router";
import { Observable } from "rxjs";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Absence } from "../models/Absence.model";
import { ApiCommunicationService } from "../services/ApiCommunication.service";

@Injectable()
export class GetEmployeeAbsences implements Resolve<Absence[]> {
  constructor(private api: ApiCommunicationService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): Absence[] | Observable<Absence[]> | Promise<Absence[]> {
    return this.api.absence().getAbsences();
  }
}
