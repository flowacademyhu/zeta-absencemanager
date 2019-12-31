import { AbsenceType, Status } from './Absence.model';

export class AbsencesPagedRequest {
    constructor(page: number, limit: number, administrationID?: number, type?: AbsenceType, status?: Status, reporter?: number, assignee?: number,
                start?: any, finish?: any, dayStart?: number, dayEnd?: number, sort?: string) {
        this.page = page;
        if (limit) {
            this.size = limit;
        }
        this.administrationID = administrationID;
        this.type = type;
        this.status = status;
        this.reporter = reporter;
        this.assignee = assignee;
        this.start = start;
        this.finish = finish;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.sort = sort;
    }
    
    page: number;
    size: number;
    administrationID?: number;
    type?: AbsenceType;
    status?: Status; 
    reporter?: number; 
    assignee?: number;
    start?: any; 
    finish?: any; 
    dayStart?: number;
    dayEnd?: number;
    sort?: string
}
