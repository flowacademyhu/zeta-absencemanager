import { Pipe, PipeTransform } from "@angular/core";
import { Status } from "../models/Absence.model";
import { User } from '../models/User.model';

@Pipe({ name: "dates" })
export class DateFormatPipe implements PipeTransform {
  transform(user: User): string {
    switch (user) {
      case user.dateOfBirth:
        switch (user.dateOfBirth) {
          case (user.dateOfBirth[1] < 10 && user.dateOfBirth[2] > 9):
            return "user.dateOfBirth[0] }}.0{{ user.dateOfBirth[1] }}.{{user.dateOfBirth[2]";
          case (user.dateOfBirth[1] > 9 && user.dateOfBirth[2] < 10):
            return "user.dateOfBirth[0] }}.{{ user.dateOfBirth[1] }}.0{{user.dateOfBirth[2]";
          case (user.dateOfBirth[1] < 10 && user.dateOfBirth[2] < 10):
            return "user.dateOfBirth[0] }}.0{{ user.dateOfBirth[1] }}.0{{user.dateOfBirth[2]";
          case (user.dateOfBirth[1] > 9 && user.dateOfBirth[2] > 9):
            return "user.dateOfBirth[0] }}.{{ user.dateOfBirth[1] }}.{{user.dateOfBirth[2]";
        }
    }
  }
}

          /* case (user.dateOfBirth[1] < 10 && user.dateOfBirth[2] > 9):
            return "user.dateOfBirth[0] }}.0{{ user.dateOfBirth[1] }}.{{user.dateOfBirth[2]";
          case (user.dateOfBirth[1] > 9 && user.dateOfBirth[2] < 10):
            return "user.dateOfBirth[0] }}.{{ user.dateOfBirth[1] }}.0{{user.dateOfBirth[2]";
          case (user.dateOfBirth[1] < 10 && user.dateOfBirth[2] < 10):
            return "user.dateOfBirth[0] }}.0{{ user.dateOfBirth[1] }}.0{{user.dateOfBirth[2]";
          case (user.dateOfBirth[1] > 9 && user.dateOfBirth[2] > 9):
            return "user.dateOfBirth[0] }}.{{ user.dateOfBirth[1] }}.{{user.dateOfBirth[2]"; */

      /* case user.dateOfEntry:
        if (user.dateOfEntry[1] < 10 && user.dateOfEntry[2] > 9) {
          return "user.dateOfEntry[0] }}.0{{ user.dateOfEntry[1] }}.{{user.dateOfEntry[2]";
        }
        if (user.dateOfEntry[1] > 9 && user.dateOfEntry[2] < 10) {
          return "user.dateOfEntry[0] }}.{{ user.dateOfEntry[1] }}.0{{user.dateOfEntry[2]";
        }
        if (user.dateOfEntry[1] < 10 && user.dateOfEntry[2] < 10) {
          return "user.dateOfEntry[0] }}.0{{ user.dateOfEntry[1] }}.0{{user.dateOfEntry[2]";
        }
        if (user.dateOfEntry[1] > 9 && user.dateOfEntry[2] > 9) {
          return "user.dateOfEntry[0] }}.{{ user.dateOfEntry[1] }}.{{user.dateOfEntry[2]";
        }
      case user.dateOfEndTrial:
        if (user.dateOfEndTrial[1] < 10 && user.dateOfEndTrial[2] > 9) {
          return "user.dateOfEndTrial[0] }}.0{{ user.dateOfEndTrial[1] }}.{{user.dateOfEndTrial[2]";
        }
        if (user.dateOfEndTrial[1] > 9 && user.dateOfEndTrial[2] < 10) {
          return "user.dateOfEndTrial[0] }}.{{ user.dateOfEndTrial[1] }}.0{{user.dateOfEndTrial[2]";
        }
        if (user.dateOfEndTrial[1] < 10 && user.dateOfEndTrial[2] < 10) {
          return "user.dateOfEndTrial[0] }}.0{{ user.dateOfEndTrial[1] }}.0{{user.dateOfEndTrial[2]";
        }
        if (user.dateOfEndTrial[1] > 9 && user.dateOfEndTrial[2] > 9) {
          return "user.dateOfEndTrial[0] }}.{{ user.dateOfEndTrial[1] }}.{{user.dateOfEndTrial[2]";
        } */
