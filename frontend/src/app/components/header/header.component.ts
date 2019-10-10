import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd } from '@angular/router';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private router : Router, private sessionService : SessionService) { }

  ngOnInit() {
  }


  public logout(){
    this.sessionService.logout();
    this.router.navigateByUrl('');
  }

}
