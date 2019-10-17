import { AbstractApiConnector } from "./AbstractApiConnector";
import { Observable } from "rxjs";
import { Group } from '../Group.model';

export class GroupApiConnector extends AbstractApiConnector {
  protected apiRoute: string = this.apiBaseUrl + "admin/group";

  public getGroup(id: number): Observable<any> {
    return this.http.get(this.apiRoute + id);
  }

  public getGroups(): Observable<any> {
    return this.http.get(this.apiRoute);
  }

  public createGroup(group: Group): Observable<any> {
    return this.http.post(this.apiRoute, group);
  }

  public updateGroup(id: number, group: Group): Observable<any> {
    return this.http.put(this.apiRoute + id, group, {});
  }

  public deleteGroup(id: number): Observable<any> {
    return this.http.delete(this.apiRoute + id);
  }
}