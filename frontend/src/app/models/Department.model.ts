import { User } from 'src/app/models/User.model';
import { Group } from 'src/app/models/Group.model';

export class Department {
    constructor(
        public id: number,
        public name: string,
        public leaders: User[], 
        public groups: Group[]
    ){}
}





