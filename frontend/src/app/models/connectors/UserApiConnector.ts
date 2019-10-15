import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';

export class UserApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'admin/user';

    getUser(id: number): Observable<any> {
        return this.http.get(this.apiRoute + id);
    }

    getUsers(): Observable<any> {
        return this.http.get(this.apiRoute);
    }
}