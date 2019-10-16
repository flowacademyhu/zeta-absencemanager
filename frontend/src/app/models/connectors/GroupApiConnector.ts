import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';
import { Group } from '../Group.model';

export class GroupApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'admin/group/';

    public getGroup(id: number): Observable<Group> {
        return this.http.get(this.apiRoute + id) as Observable<Group>;
    }

    public getGroups(): Observable<Group> {
        return this.http.get(this.apiRoute) as Observable<Group>;
    }

    public createGroup(group: Group): Observable<Group> {
        return this.http.post(this.apiRoute, group) as Observable<Group>;
    }

    public updateGroup(id: number, group: Group): Observable<Group> {
        return this.http.put(this.apiRoute + id, group, {}) as Observable<Group>;
    }

    public deleteGroup(id: number): Observable<Group> {
        return this.http.delete(this.apiRoute + id) as Observable<Group>;
    }
}