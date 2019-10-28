import { AbstractApiConnector } from "./AbstractApiConnector";
import { Observable } from "rxjs";
import { Absence } from "../Absence.model";
import { HttpParams } from '@angular/common/http';

export class AdminAbsenceApiConnector extends AbstractApiConnector {
  protected apiRoute: string = this.apiBaseUrl + "admin/absence/";

  public getAbsence(id: number): Observable<any> {
    return this.http.get(this.apiRoute + id);
  }

  public getAbsences(): Observable<any> {
    return this.http.get(this.apiRoute);
  }

  public createAbsence(absence: Absence): Observable<any> {
    return this.http.post(this.apiRoute, absence);
  }

  public updateAbsence(id: number, absence: Absence): Observable<any> {
    return this.http.put(this.apiRoute + id, absence, {});
  }

  //TODO backend delete method does not exist yet
  public deleteAbsence(id: number): Observable<any> {
    return this.http.delete(this.apiRoute + id);
  }

  public getAbsencesPage(size: number, page: number): Observable<any> {
    console.log('Size ' + size + 'pageNumber ' + page);
    return this.http.get(this.apiRoute + 'page', {
      params: new HttpParams()
        .set('size', size.toString())
        .set('page', page.toString())
    });
  }
}