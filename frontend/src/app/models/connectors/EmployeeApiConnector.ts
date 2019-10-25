import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';
import { User } from '../User.model';

export class EmployeeApiConnector extends AbstractApiConnector {

    protected apiRoute: string = this.apiBaseUrl + 'user';

    public getCurrent(): Observable<User> {
        return this.http.get(this.apiRoute) as Observable<User>;
    }

    sendPassword(firstPassword: string, secondPassword: string, id: number): Observable<User>{
        return this.http.put("localhost:8080/user/changepassw/" + id, {firstPassword, secondPassword}) as Observable<User>; 
    }

}