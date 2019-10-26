import { Pipe, PipeTransform } from "@angular/core";

@Pipe({ name: "type" })
export class AbsenceTypePipe implements PipeTransform {
  transform(type: String): string {
    switch (type) {
      case "ABSENCE":
        return "Szabadság";
      case "NON_WORKING":
        return "Keresőképtelenség";
      case "CHILD_SICK_PAY":
        return "Gyermek táppénz";
      case "UNPAID_HOLIDAY":
        return "Fizetés nélküli szabadság";
    }
  }
}
