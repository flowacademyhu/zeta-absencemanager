import { Component, OnInit } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';
import { User } from 'src/app/models/User.model';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-employee-show',
  templateUrl: './employee-show.component.html',
  styleUrls: ['./employee-show.component.css']
})
export class EmployeeShowComponent implements OnInit {

  private user: User = new User();
  public name = "employee";
  public dob = '1990-10-11';
  public doe = '2000-10-10';
  public trialexp = '2001-01-01';
  public position = 'melós';
  public supervisor = "Git Áron"
  public numberOfChildren = 3;
  public other = '';

  public total = 20;
  public used = 10;
  public aviable = this.total-this.used; 
  public totalrate = this.used/this.total


  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  loaduser() {
    this.userService.getUsers().subscribe((user) => {
      this.user = user;
    })
  }
}
