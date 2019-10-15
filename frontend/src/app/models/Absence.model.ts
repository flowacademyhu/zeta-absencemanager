import { User } from './User.model';
import { DataEntity } from './DataEntity';

//TODO enum names imported from backend, we should consider the names as well
export enum AbsenceType {
    ABSENCE,
    NON_WORKING,
    CHILD_SICK_PAY,
    UNPAID_HOLIDAY
}

export enum Status {
    OPEN,
    UNDER_REVIEW,
    APPROVED,
    ADMINISTRATED,
    DONE,
    REJECT
}

export class Absence extends DataEntity{
        public type: AbsenceType;
        public summary: string;
        public begin: Date;
        public end: Date;
        public reporter: User;
        public assignee: User;
        public status: Status;

    constructor(type: AbsenceType, summary: string, begin: Date, end: Date, reporter?: User, assignee?: User) {
        super();
        this.type = type;
        this.summary = summary;
        this.begin = begin;
        this.end = end;
        this.reporter = reporter;
        this.assignee = assignee;
    }
}



