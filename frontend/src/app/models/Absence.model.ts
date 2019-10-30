import { User } from "./User.model";
import { DataEntity } from "./DataEntity.model";

//TODO enum names imported from backend, we should consider the names as well
export enum AbsenceType {
  ABSENCE = "ABSENCE",
  NON_WORKING = "NON_WORKING",
  CHILD_SICK_PAY = "CHILD_SICK_PAY",
  UNPAID_HOLIDAY = "UNPAID_HOLIDAY"
}

export enum Status {
  OPEN = "OPEN",
  UNDER_REVIEW = "UNDER_REVIEW",
  APPROVED = "APPROVED",
  ADMINISTRATED = "ADMINISTRATED",
  DONE = "DONE",
  REJECTED = "REJECTED"
}

export class Absence extends DataEntity {
  public type: AbsenceType;
  public summary: string;
  public begin: any;
  public end: any;
  public duration: number;
  public reporter: User;
  public assignee: User;
  public status: Status;
  public administrationID: number;

  constructor(
    type: AbsenceType,
    summary: string,
    begin: any,
    end: any,
    duration: number,
    reporter?: User,
    assignee?: User,
    administrationID?: number
  ) {
    super();
    this.type = type;
    this.summary = summary;
    this.begin = begin;
    this.end = end;
    if (reporter) {
      this.reporter = reporter;
    }
    if (assignee) {
      this.assignee = assignee;
    }
    this.duration = duration;
    if (administrationID) {
      this.administrationID = administrationID;
    }
  }

  public static enumSelector(definition) {
    return Object.keys(definition);
  }

  public static convertDate(date: Date): number[] {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    var day = date.getDate();
    var dateArray: number[] = [year, month, day];
    return dateArray;
  }
}
