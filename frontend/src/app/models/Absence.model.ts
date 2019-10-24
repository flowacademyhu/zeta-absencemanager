import { User } from "./User.model";
import { DataEntity } from "./DataEntity.model";

//TODO enum names imported from backend, we should consider the names as well
export enum AbsenceType {
  ABSENCE = "Absence",
  NON_WORKING = "Non Working",
  CHILD_SICK_PAY = "Child Sick Pay",
  UNPAID_HOLIDAY = "Unpaid Holiday"
}

export enum Status {
  OPEN = "Open",
  UNDER_REVIEW = "Under Review",
  APPROVED = "Approved",
  ADMINISTRATED = "Administrated",
  DONE = "Done",
  REJECT = "Reject"
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

  constructor(
    type: AbsenceType,
    summary: string,
    begin: any,
    end: any,
    duration: number,
    reporter?: User,
    assignee?: User
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
  }

  public static enumSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  }
}
