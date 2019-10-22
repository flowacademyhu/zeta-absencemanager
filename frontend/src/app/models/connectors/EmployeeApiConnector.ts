import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';
import { User } from '../User.model';

export class EmployeeApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'user';

    public getCurrent(): Observable<User> {
        return this.http.get(this.apiRoute) as Observable<User>;
    }

    public updateCurrent(id: number,user: User): Observable<User> {
        return this.http.put(this.apiRoute + "/" + id, user, {}) as Observable<User>;
    }

}