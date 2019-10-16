import { User } from 'src/app/models/User.model';
import { DataEntity } from './DataEntity';

export class Group extends DataEntity {
        public name: string;
        public parentId: number;
        public leaders: User[]; 
        public employees: User[];

    constructor(name: string, parentId?: number, leaders?: User[], employees?: User[]) {
        super();
        this.name = name;
        this.parentId = parentId;
        this.leaders = leaders;
        this.employees = employees;
    }
}


