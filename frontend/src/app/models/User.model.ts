import { Group } from "src/app/models/Group.model";
import { Absence } from "./Absence.model";
import { DataEntity } from "./DataEntity.model";

export class User extends DataEntity {
  public firstName: string;
  public lastName: string;
  public email: string;
  public password: string;
  public dateOfBirth: any;
  public dateOfEntry: any;
  public dateOfEndTrial: any;
  public isOnTrial: boolean;
  public position: string;
  public numberOfChildren: number;
  public otherAbsenceEnt: string;
  public group: Group;
  public role: string;
  public totalAbsenceDays: number;
  public totalSickLeaveDays: number;
  public usedAbsenceDays: number;
  public usedSickLeaveDays: number;
  public absences: Absence[];
  public usedChildSickPay: number;

  //TODO for now, added some optional variables to constructor
  constructor(
    firstName?: string,
    lastName?: string,
    email?: string,
    password?: string,
    dateOfBirth?: any,
    dateOfEntry?: any,
    position?: string,
    numberOfChildren?: number,
    group?: Group,
    totalAbsenceDays?: number,
    totalSickLeaveDays?: number,
    usedAbsenceDays?: number,
    usedSickLeaveDays?: number,
    usedChildSickPay?: number
  ) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.dateOfBirth = dateOfBirth;
    this.dateOfEntry = dateOfEntry;
    this.position = position;
    this.numberOfChildren = numberOfChildren;
    this.group = group;
    this.totalAbsenceDays = totalAbsenceDays;
    this.usedAbsenceDays = usedAbsenceDays;
    this.totalSickLeaveDays = totalSickLeaveDays;
    this.usedSickLeaveDays = usedSickLeaveDays;
    this.usedChildSickPay = usedChildSickPay;
  }
}
