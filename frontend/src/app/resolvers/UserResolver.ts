import { Injectable } from "@angular/core";
import { Resolve } from "@angular/router";
import { Observable } from "rxjs";
import { ActivatedRouteSnapshot } from "@angular/router";
import { User } from "../models/User.model";
import { ApiCommunicationService } from "../services/api-communication.service";
import { PagedResponse } from "../models/PagedResponse.model";
import { UsersPagedRequest } from "../models/UsersPagedRequest";

@Injectable()
export class UserResolver implements Resolve<PagedResponse<User>> {
  constructor(private api: ApiCommunicationService) {}

  resolve(
    route: ActivatedRouteSnapshot
  ):
    | PagedResponse<User>
    | Observable<PagedResponse<User>>
    | Promise<PagedResponse<User>> {
    const page = route.queryParams["page"];
    const size = route.queryParams["size"];
    const name = route.queryParams["name"];
    const dateOfEntryStart = route.queryParams["dateOfEntryStart"];
    const dateOfEntryFinish = route.queryParams["dateOfEntryFinish"];
    const dateOfEndTrialStart = route.queryParams["dateOfEndTrialStart"];
    const dateOfEndTrialFinish = route.queryParams["dateOfEndTrialFinish"];
    const group = route.queryParams["group"];
    const position = route.queryParams["position"];
    const role = route.queryParams["role"];
    const pagedRequest = new UsersPagedRequest(
      page ? page : 0,
      size ? size : 5,
      name,
      dateOfEntryStart,
      dateOfEntryFinish,
      dateOfEndTrialStart,
      dateOfEndTrialFinish,
      group,
      position,
      role
    );
    return this.api.user().getUsersPaginated(pagedRequest);
  }
}
