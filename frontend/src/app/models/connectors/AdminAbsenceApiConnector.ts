import { AbstractApiConnector } from "./AbstractApiConnector";
import { Observable } from "rxjs";
import { Absence } from "../Absence.model";
import { HttpParams } from '@angular/common/http';
import { PagedResponse } from '../PagedResponse.model';
import { AbsencesPagedRequest } from '../AbsencesPagedRequest';

export class AdminAbsenceApiConnector extends AbstractApiConnector {
  protected apiRoute: string = this.apiBaseUrl + "admin/absence/";

  public getAbsence(id: number): Observable<any> {
    return this.http.get(this.apiRoute + id);
  }

  public getAbsences(pagedRequest: AbsencesPagedRequest): Observable<PagedResponse<Absence>> {
    let httpParams = new HttpParams();
    Object.keys(pagedRequest).forEach(function (key) {
      if(pagedRequest[key]){
      httpParams = httpParams.append(key, pagedRequest[key]);
      }
    });
    console.log(httpParams);
    return this.http.get(this.apiRoute, {params: httpParams}) as Observable<PagedResponse<Absence>>;
  }

  public createAbsence(absence: Absence): Observable<any> {
    return this.http.post(this.apiRoute, absence);
  }

  //TODO any type to ABSENCE
  public updateAbsence(id: number, absence: any): Observable<any> {
    return this.http.put(this.apiRoute + id, absence, {});
  }

  //TODO backend delete method does not exist yet
  public deleteAbsence(id: number): Observable<any> {
    return this.http.delete(this.apiRoute + id);
  }

}