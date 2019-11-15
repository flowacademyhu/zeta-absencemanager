import { Injectable } from "@angular/core";
import { Resolve } from "@angular/router";
import { Observable } from "rxjs";
import { ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { User } from "../models/User.model";
import { ApiCommunicationService } from "../services/api-communication.service";
import { PagedResponse } from "../models/PagedResponse.model";
import { UsersPagedRequest } from "../models/UsersPagedRequest";

@Injectable()
export class UserResolver implements Resolve<PagedResponse<User>> {
  constructor(private api: ApiCommunicationService) {}

  resolve(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ):
    | PagedResponse<User>
    | Observable<PagedResponse<User>>
    | Promise<PagedResponse<User>> {
    const page = +route.queryParams["page"];
    const size = +route.queryParams["size"];
    const pagedRequest = new UsersPagedRequest(
      page ? page : 0,
      size ? size : 5
    );
    return this.api.user().getUsersPaginated(pagedRequest);
  }
}
