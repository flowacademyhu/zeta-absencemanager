import { User } from './User.model';

export class Absence {
    constructor(
        public id: number,
        public createdAt: Date,
        public summary: number,
        public begin: Date,
        public end: Date,
        public reporter: User,
        public assignee: User,
        public status: string,
        public type: string
        

    ){}
}



