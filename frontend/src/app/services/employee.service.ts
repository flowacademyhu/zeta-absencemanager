import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { AdminAbsencesIndexComponent } from '../components/admin/absences-index/admin-absences-index.component';
import { Observable } from 'rxjs';
import { Absence } from '../models/Absence.model';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {

  private url: string = "localhost:8080"

  constructor(private httpclient : HttpClient) { }


//  getEmpAbsence(): Observable<Absence[]>{
//    return this.httpclient.get<Absence[]>(this.url);
//  }
}
