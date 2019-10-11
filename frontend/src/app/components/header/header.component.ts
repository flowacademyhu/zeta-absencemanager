import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  public isLoggedIn$ : Observable<boolean>;

  constructor(private router : Router, private sessionService : SessionService) { 
    this.isLoggedIn$ = sessionService.isLoggedIn();
  }

  ngOnInit() {
  }


  public logout(){
    this.sessionService.logout().then(() => {
      this.router.navigateByUrl('');
    });
  }


}
