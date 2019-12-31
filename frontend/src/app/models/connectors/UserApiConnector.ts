import { AbstractApiConnector } from "./AbstractApiConnector";
import { Observable } from "rxjs";
import { User } from "../User.model";
import { PagedResponse } from "../PagedResponse.model";
import { UsersPagedRequest } from "../UsersPagedRequest";
import { HttpParams } from "@angular/common/http";

export class UserApiConnector extends AbstractApiConnector {
  protected apiRoute: string = this.apiBaseUrl + "admin/user";

  public getUser(id: number): Observable<any> {
    return this.http.get(this.apiRoute + "/" + id);
  }

  public getUsersPaginated(
    pagedRequest: UsersPagedRequest
  ): Observable<PagedResponse<User>> {
    let httpParams = new HttpParams();
    Object.keys(pagedRequest).forEach(function(key) {
      if (pagedRequest[key]) {
        httpParams = httpParams.append(key, pagedRequest[key]);
      }
    });
    return this.http.get(this.apiRoute + "/paginated", {
      params: httpParams
    }) as Observable<PagedResponse<User>>;
  }

  public getUsers(): Observable<User[]> {
    return this.http.get(this.apiRoute) as Observable<User[]>;
  }

  public getLeaders(): Observable<any> {
    return this.http.get(this.apiRoute + "/leaders");
  }

  public getEmployees(): Observable<any> {
    return this.http.get(this.apiRoute + "/employees") as Observable<User[]>;
  }

  public getEmployeesByGroup(id: number): Observable<any> {
    return this.http.get(this.apiRoute + "/employees/" + id) as Observable<
      User[]
    >;
  }

  public getEverybodyByGroup(id: number): Observable<any> {
    return this.http.get(this.apiRoute + "/employees/group/" + id);
  }

  public createUser(user: User): Observable<any> {
    return this.http.post(this.apiRoute, user, {}) as Observable<User[]>;
  }

  public updateUser(id: number, user: User): Observable<any> {
    return this.http.put(this.apiRoute + "/" + id, user, {});
  }

  public updateSelfUser(id: number, user: User): Observable<any> {
    return this.http.put(this.apiBaseUrl + "user/" + id, user, {});
  }

  //TODO backend delete method is void yet
  public deleteUser(id: number): Observable<any> {
    return this.http.delete(this.apiRoute + "/" + id) as Observable<any>;
  }
}
