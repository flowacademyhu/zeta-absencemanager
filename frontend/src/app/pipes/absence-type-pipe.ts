import { Pipe, PipeTransform } from "@angular/core";
import { AbsenceType } from "../models/Absence.model";

@Pipe({ name: "type" })
export class AbsenceTypePipe implements PipeTransform {
  transform(type: AbsenceType): string {
    switch (type) {
      case AbsenceType.ABSENCE:
        return "Szabadság";
      case AbsenceType.NON_WORKING:
        return "Keresőképtelenség";
      case AbsenceType.CHILD_SICK_PAY:
        return "Gyermek táppénz";
      case AbsenceType.UNPAID_HOLIDAY:
        return "Fizetés nélküli szabadság";
    }
  }
}
