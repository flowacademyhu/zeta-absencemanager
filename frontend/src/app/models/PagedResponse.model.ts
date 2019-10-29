export interface PagedResponse<T> {
    embedded: T[];
    metadata: MetaData;

}

export interface MetaData {
    totalElements: number;
    totalPages: number;
    pageSize: number;
    pageNumber: number;
}