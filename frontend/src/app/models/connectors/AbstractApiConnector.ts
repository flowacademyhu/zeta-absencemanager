import { HttpClient, HttpHeaders } from '@angular/common/http';
import { environment } from 'src/environments/environment';


export enum Connectors {
    AUTH,
    USER,
    ABSENCE
}

export abstract class AbstractApiConnector<T> {

    protected readonly baseURL = environment.baseURL;

    protected httpOptions = {
        headers: new HttpHeaders({
          'Authorization': 'Basic ' + btoa("fooClientIdPassword:secret"),
          'Content-type': 'application/x-www-form-urlencoded'
        })
      };

    protected constructor(protected _http: HttpClient, protected connector : Connectors){
    }

    public get<T>(){
    }

}