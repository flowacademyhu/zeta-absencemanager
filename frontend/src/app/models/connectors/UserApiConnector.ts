import { AbstractApiConnector } from "./AbstractApiConnector";
import { Observable } from "rxjs";
import { User } from "../User.model";

export class UserApiConnector extends AbstractApiConnector {
  protected apiRoute: string = this.apiBaseUrl + "admin/user";

  public getUser(id: number): Observable<any> {
    return this.http.get(this.apiRoute + "/" + id);
  }

  public getUsers(): Observable<any> {
    return this.http.get(this.apiRoute) as Observable<User[]>;
  }

  public getLeaders(): Observable<any> {
    return this.http.get(this.apiRoute + "/leaders");
  }

  public createUser(user: User): Observable<any> {
    return this.http.post(this.apiRoute, user);
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
