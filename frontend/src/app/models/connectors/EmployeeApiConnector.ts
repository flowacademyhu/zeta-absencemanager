import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';
import { User } from '../User.model';

export class EmployeeApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'user';

    public getCurrent(): Observable<User> {
        return this.http.get(this.apiRoute) as Observable<User>;
    }

    public changePassword(id: number, firstPassword: String, secondPassword: String): Observable<User> {
        return this.http.put(this.apiRoute + "/changepassw/" + id, {dataA: firstPassword, dataB: secondPassword}) as Observable<User>;
    }

}