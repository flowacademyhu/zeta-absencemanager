import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';
import { User } from '../User.model';

export class EmployeeApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'user';

    public getCurrent(): Observable<User> {
        return this.http.get(this.apiRoute) as Observable<User>;
    }

    public changePassw(firstPassword: string, secondPassword: string, oldPassword: string): Observable<User> {
        return this.http.post(this.apiRoute + "/changepassw", {firstPassword, secondPassword, oldPassword}) as Observable<User>;
    }

    public deleteProfile(id: number): Observable<User> {
        return this.http.delete(this.apiRoute + "/" + id) as Observable<User>;
    }
}