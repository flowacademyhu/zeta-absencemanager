import { Group } from "./Group.model";

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
    role?: string,
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
    this.role = role;
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
  role?: string;
  sort?: string;
}
