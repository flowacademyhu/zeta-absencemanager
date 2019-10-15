import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';

export class UserApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'user';

    getUser(id: number): Observable<User> {
        return this.http.get(this.apiRoute + id);
    }
}