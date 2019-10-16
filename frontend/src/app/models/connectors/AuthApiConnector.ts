import { AbstractApiConnector } from './AbstractApiConnector';
import { Observable } from 'rxjs';
import { HttpParams, HttpHeaders } from '@angular/common/http';

export class AuthApiConnector extends AbstractApiConnector {
   
    protected apiRoute: string = this.apiBaseUrl + 'oauth/token';


    public getToken(username: string, password: string): Observable<any> {
        const body = new HttpParams()
            .set('username', username)
            .set('password', password)
            .set('grant_type', 'password');
        
        const httpOptions = {
            headers: new HttpHeaders({
              'Authorization': 'Basic ' + btoa("fooClientIdPassword:secret"),
              'Content-type': 'application/x-www-form-urlencoded'
            })
          };

        return this.http.post(this.apiRoute, body, httpOptions);
    }
}