export class UsersPagedRequest {
  constructor(page: number, size: number) {
    this.page = page;
    if (size) {
      this.size = size;
    }
  }

  page: number;
  size: number;
}
