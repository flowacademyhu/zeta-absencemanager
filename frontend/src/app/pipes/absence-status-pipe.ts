import { Pipe, PipeTransform } from "@angular/core";
import { Status } from "../models/Absence.model";

@Pipe({ name: "status" })
export class AbsenceStatusPipe implements PipeTransform {
  transform(status: Status): string {
    switch (status) {
      case Status.OPEN:
        return "Nyitott";
      case Status.UNDER_REVIEW:
        return "Jóváhagyásra vár";
      case Status.APPROVED:
        return "Jóváhagyott";
      case Status.ADMINISTRATED:
        return "Kiírt";
      case Status.DONE:
        return "Lezárult";
      case Status.REJECTED:
        return "Törölt";
    }
  }
}
