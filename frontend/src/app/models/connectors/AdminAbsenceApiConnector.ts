import { AbstractApiConnector } from "./AbstractApiConnector";
import { Observable } from "rxjs";
import { Absence } from "../Absence.model";
import { HttpParams } from '@angular/common/http';
import { PagedResponse } from '../PagedResponse.model';

export class AdminAbsenceApiConnector extends AbstractApiConnector {
  protected apiRoute: string = this.apiBaseUrl + "admin/absence/";

  public getAbsence(id: number): Observable<any> {
    return this.http.get(this.apiRoute + id);
  }

  public getAbsences(size: number, page: number, administrationId?: number): Observable<PagedResponse<Absence>> {
    return this.http.get(this.apiRoute, {
      params: new HttpParams()
        .set('size', size.toString())
        .set('page', page.toString())
        .set('administrationId', administrationId.toString())
    }) as Observable<PagedResponse<Absence>>;
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