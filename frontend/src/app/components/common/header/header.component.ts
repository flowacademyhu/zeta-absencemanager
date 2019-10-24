import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { Observable, Subject } from 'rxjs';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { User } from 'src/app/models/User.model';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit, OnDestroy {

  public isLoggedIn$ : Observable<boolean>;
  private _unsubscribe$ = new Subject<void>();
  public user: User;

  constructor(private router : Router, private sessionService : SessionService, private api: ApiCommunicationService) { 
    this.isLoggedIn$ = sessionService.isLoggedIn();
    console.log(this.isLoggedIn$);
    sessionService.userData
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(user => this.user = user);
  }

  ngOnInit() {
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete(); 
  }


  public logout(){
    this.sessionService.logout();
  }

}
