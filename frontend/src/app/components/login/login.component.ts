import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import { Router } from '@angular/router';
import { first } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  private credentials: FormGroup = new FormGroup({
    username: new FormControl('', Validators.required),
    password: new FormControl('', Validators.required),
  });

  @Input()
  error: string | null;

  constructor(private sessionService : SessionService, private router : Router) { }

  ngOnInit() {
  }

  submit() {
    if (this.credentials.valid) {
     this.sessionService.login(this.credentials.value.username, this.credentials.value.password);
     this.router.navigate(["admin/absence-index"]);
    }
  }

}
