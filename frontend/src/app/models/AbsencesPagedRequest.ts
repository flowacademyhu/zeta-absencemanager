export class AbsencesPagedRequest {
    constructor(page: number, limit: number, administrationId?: number) {
        this.pageNumber = page;
        this.administrationId = administrationId;
        if (limit) {
            this.pageSize = limit;
        }
    }
    pageNumber: number;
    pageSize?: number;
    administrationId: number;
}
