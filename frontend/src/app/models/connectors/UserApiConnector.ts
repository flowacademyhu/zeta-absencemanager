import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';

export class UserApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'admin/user/';

    public getUser(id: number): Observable<any> {
        return this.http.get(this.apiRoute + id);
    }

    public getUsers(): Observable<any> {
        return this.http.get(this.apiRoute);
    }

    //TODO after modifying models, change 'any' to User
    public createUser(user: any): Observable<any> {
        return this.http.post(this.apiRoute, user);
    }

    public updateUser(id: number, user: any): Observable<any> {
        return this.http.put(this.apiRoute + id, user, {});
    }

    //TODO backend delete method is void yet
    public deleteUser(id: number): Observable<any> {
        return this.http.delete(this.apiRoute + id);
    }
}