import { AbstractApiConnector } from "./AbstractApiConnector";
import { Observable } from "rxjs";
import { Absence } from "../Absence.model";

export class AbsenceApiConnector extends AbstractApiConnector {
  protected apiRoute: string = this.apiBaseUrl + "absence/";

  public getAbsence(id: number): Observable<any> {
    return this.http.get(this.apiRoute + id);
  }

  public getAbsences(): Observable<any> {
    return this.http.get(this.apiRoute);
  }

  public createAbsence(absence: Absence): Observable<any> {
    return this.http.post(this.apiRoute, absence);
  }

  //TODO absence to Absence
  public updateAbsence(id: number, absence: any): Observable<any> {
    return this.http.put(this.apiRoute + id, absence, {});
  }

  //TODO backend delete method does not exist yet
  public deleteAbsence(id: number): Observable<any> {
    return this.http.delete(this.apiRoute + id);
  }
}
