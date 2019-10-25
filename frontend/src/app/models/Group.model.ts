import { DataEntity } from "./DataEntity.model";

export class Group extends DataEntity {
  constructor(
    public name: string,
    public parentId?: number,
    public leaderId?: number
  ) {
    super();
  }
}
