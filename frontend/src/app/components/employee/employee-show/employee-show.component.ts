import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormsModule, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';


@Component({
  selector: 'app-employee-show',
  templateUrl: './employee-show.component.html',
  styleUrls: ['./employee-show.component.css']
})
export class EmployeeShowComponent implements OnInit {
    private _unsubscribe$: Subject<boolean> = new Subject();
    public user: User;

  
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
  
      constructor(private userService: UserService,  private formbuild: FormBuilder, private route: ActivatedRoute) { }

    ngOnInit() {
      this.route.data
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((data: any) => {
        this.user = data.user;
        console.log(this.user);
        this.user;
      })
  this.showuser();
    }
  
    ngOnDestroy(): void {
      this._unsubscribe$.next();
      this._unsubscribe$.complete();
      
    }
    
  showuser(){
    this.userService.getUser(1).subscribe((user) => {
    this.user = user;
       })

       
 }
}

  







  
  


  

  

