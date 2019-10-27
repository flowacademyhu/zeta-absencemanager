import { Pipe, PipeTransform } from "@angular/core";
import { Status } from "../models/Absence.model";

@Pipe({ name: "status" })
export class AbsenceStatusPipe implements PipeTransform {
  transform(status: String): string {
    switch (status) {
      case Status[Status.OPEN]:
        return "Nyitott";
      case Status[Status.UNDER_REVIEW]:
        return "Jóváhagyásra vár";
      case Status[Status.APPROVED]:
        return "Jóváhagyott";
      case Status[Status.ADMINISTRATED]:
        return "Kiírt";
      case Status[Status.DONE]:
        return "Lezárult";
      case Status[Status.REJECT]:
        return "Törölt";
    }
  }
}
