import { Pipe, PipeTransform } from "@angular/core";
import { AbsenceType } from "../models/Absence.model";

@Pipe({ name: "type" })
export class AbsenceTypePipe implements PipeTransform {
  transform(type: String): string {
    switch (type) {
      case AbsenceType[AbsenceType.ABSENCE]:
        return "Szabadság";
      case AbsenceType[AbsenceType.NON_WORKING]:
        return "Keresőképtelenség";
      case AbsenceType[AbsenceType.CHILD_SICK_PAY]:
        return "Gyermek táppénz";
      case AbsenceType[AbsenceType.UNPAID_HOLIDAY]:
        return "Fizetés nélküli szabadság";
    }
  }
}
