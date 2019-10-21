import { Injectable } from '@angular/core';
import { ApiCommunicationService } from './ApiCommunication.service';
import { User } from '../models/User.model';

@Injectable({
  providedIn: 'root'
})
export class AppInitService {

  public _userData$: any;
  constructor(private api: ApiCommunicationService) { }

/*   init() {
 
    return new Promise<void>((resolve, reject) => {
        console.log("AppInitService.init() called");

        setTimeout(() => {
            console.log('AppInitService Finished');
            resolve();
        }, 6000);

    });
  } */

  async initData(): Promise<User> {
    this._userData$ = this.api.employee().getCurrent();
    console.log(this._userData$);
    return await this._userData$.toPromise();
  }
}
