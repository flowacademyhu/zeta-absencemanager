import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, FormsModule, FormBuilder, Validators } from '@angular/forms';
import { User } from 'src/app/models/User.model';
import { CommonModule } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { UserService } from 'src/app/services/user.service';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { ChangePasswComponent } from 'src/app/modals/change-passw/change-passw.component';


@Component({
  selector: 'app-employee-show',
  templateUrl: './employee-show.component.html',
  styleUrls: ['./employee-show.component.css']
})
export class EmployeeShowComponent implements OnInit {
    private _unsubscribe$: Subject<boolean> = new Subject();
    public user: User;

constructor(private formbuild: FormBuilder, 
            private route: ActivatedRoute, 
            private api: ApiCommunicationService,
            public dialogRef: MatDialogRef<EmployeeShowComponent>,
            public dialog: MatDialog
            ) { }

ngOnInit() {
  this.showuser();
}

ngOnDestroy(): void {
  this._unsubscribe$.next();
  this._unsubscribe$.complete();
  }
    
showuser(){
 /*  this.api.employee().getCurrent().subscribe((user: User) => {
  this.user = user;
    })    */
  
 }

changePassw(){
  this.dialog.open(ChangePasswComponent)
}




}

  







  
  


  

  

