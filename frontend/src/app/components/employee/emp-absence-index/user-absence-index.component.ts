import { Component, OnInit } from '@angular/core';
import { EmployeeService } from 'src/app/services/employee.service';
import { Absence } from 'src/app/models/Absence.model'

@Component({
  selector: 'app-user-absence-index',
  templateUrl: './user-absence-index.component.html',
  styleUrls: ['./user-absence-index.component.css']
})
export class UserAbsenceIndexComponent implements OnInit {

  displayedColumns = Absence; 
  private empAbsence = [];

  constructor(private employeeService : EmployeeService) { }

  ngOnInit() {
   // this.employeeService.getEmpAbsence()
   //   .subscribe(
   //      data => this.empAbsence = data 
   //   );
  }

}
