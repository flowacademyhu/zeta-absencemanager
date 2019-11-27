import { DataEntity } from "./DataEntity.model";
import { User } from "./User.model";

export class Group extends DataEntity {
  constructor(
    public name: string,
    public parentId?: number,
    public leader?: DataEntity,
    public leaderId?: number,
    public minimalWorkers?: number
  ) {
    super();
  }
}
