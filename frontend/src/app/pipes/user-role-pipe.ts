import { Pipe, PipeTransform } from "@angular/core";
import { Role } from "../models/User.model";

@Pipe({ name: "role" })
export class UserRolePipe implements PipeTransform {
  transform(role: Role): string {
    switch (role) {
      case Role.ADMIN:
        return "Adminisztrátor";
      case Role.LEADER:
        return "Vezető";
      case Role.EMPLOYEE:
        return "Alkalmazott";
      case Role.INACTIVE:
        return "Inaktív";
    }
  }
}