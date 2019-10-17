import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormsModule, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';



@Component({
  selector: 'app-employee-profile-edit',
  templateUrl: './employee-profile-edit.component.html',
  styleUrls: ['./employee-profile-edit.component.css']
})
export class EmployeeProfileEditComponent implements OnInit {
  private _unsubscribe$: Subject<boolean> = new Subject();
  public user: User = new User();
  
  

  

  
  constructor(private formbuild: FormBuilder, private route: ActivatedRoute) { }

  ngOnInit() {
    this.route.data
    .pipe(takeUntil(this._unsubscribe$))
    .subscribe((data: any) => {
      this.user = data.user;
      console.log(this.user.firstName);
    })
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
    
  }

  update() {
    this.user = this.user
   
  }

  onSubmit() {
    // TODO: Use EventEmitter with form value
    
  }
}
