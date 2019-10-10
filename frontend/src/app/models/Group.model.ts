import { User } from 'src/app/models/User.model';
import { Department } from 'src/app/models/Department.model';

export class Group {
    constructor(
        public id: number,
        public name: string,
        public leaders: User[], 
        public department: Department, 
        public employees: User[]
        

    ){}
}


