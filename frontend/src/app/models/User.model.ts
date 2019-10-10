import { Group } from 'src/app/models/Group.model';
import { Department } from 'src/app/models/Department.model';
import { Absence } from './Absence.model';

export class User {
    constructor(
        public id: number,
        public firstName: string,
        public lastName: string,
        public password: string,
        public email: string,
        public dateOfBirth: Date,
        public dateOfEntry: Date,
        public dateOfEndTrial: Date,
        public isOnTrial: boolean,
        public position: string,
        public numberOfChildren: number,
        public otherAbsenceEnt: string,
        public groups: Group[],
        public departments: Department[],
        public role: string,
        public absences: Absence[],
       
        
    ){}
}














