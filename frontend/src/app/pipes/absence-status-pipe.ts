import { Pipe, PipeTransform } from "@angular/core";

@Pipe({ name: "status" })
export class AbsenceStatusPipe implements PipeTransform {
  transform(status: String): string {
    switch (status) {
      case "OPEN":
        return "Nyitott";
      case "UNDER_REVIEW":
        return "Jóváhagyásra vár";
      case "APPROVED":
        return "Jóváhagyott";
      case "ADMINISTRATED":
        return "Kiírt";
      case "DONE":
        return "Lezárult";
      case "REJECT":
        return "Törölt";
    }
  }
}
