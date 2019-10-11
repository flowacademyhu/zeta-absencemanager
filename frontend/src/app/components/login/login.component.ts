import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { SessionService, LoginRejectionReason } from 'src/app/services/session.service';

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

  public error: string;

  constructor(private sessionService : SessionService) { }

  ngOnInit() {
  }

  onSubmit() {
    if (this.credentials.valid) {
     this.sessionService.login(this.credentials.value.username, this.credentials.value.password)
      .catch( (error: LoginRejectionReason) => {
        this.error = error;
      })
     ;
    }
  }

}
