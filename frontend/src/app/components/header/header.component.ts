import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { Observable } from 'rxjs';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';
import { User } from 'src/app/models/User.model';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public isLoggedIn$ : Observable<boolean>;
  public user: User;

  constructor(private router : Router, private sessionService : SessionService, private api: ApiCommunicationService) { 
    this.isLoggedIn$ = sessionService.isLoggedIn();
  }

  ngOnInit() {
  }


  public logout(){
    this.sessionService.logout().then(() => {
      this.router.navigateByUrl('');
    });
  }

  public getCurrentRole(): void {
    this.api.employee().getCurrent().subscribe((user: User) => {
      this.user = user;
        })
  }

}
