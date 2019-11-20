import { Group } from "./Group.model";
import { Role } from "./User.model";

export class UsersPagedRequest {
  constructor(
    page?: number,
    size?: number,
    name?: string,
    dateOfEntryStart?: any,
    dateOfEntryFinish?: any,
    dateOfEndTrialStart?: any,
    dateOfEndTrialFinish?: any,
    group?: Group,
    position?: string,
    sort?: string
  ) {
    this.page = page;
    if (size) {
      this.size = size;
    }
    this.name = name;
    this.dateOfEntryStart = dateOfEntryStart;
    this.dateOfEntryFinish = dateOfEntryFinish;
    this.dateOfEndTrialStart = dateOfEndTrialStart;
    this.dateOfEndTrialFinish = dateOfEndTrialFinish;
    this.group = group;
    this.position = position;
    this.role = [Role.ADMIN, Role.LEADER, Role.EMPLOYEE];
    this.sort = sort;
  }

  page?: number;
  size?: number;
  name?: string;
  dateOfEntryStart?: any;
  dateOfEntryFinish?: any;
  dateOfEndTrialStart?: any;
  dateOfEndTrialFinish?: any;
  group?: Group;
  position?: string;
  role?: Role[];
  sort?: string;
}
