import { Group } from 'src/app/models/Group.model';
import { Absence } from './Absence.model';
import { DataEntity } from './DataEntity';

export class User extends DataEntity {
    public firstName: string;
    public lastName: string;
    public email: string;
    public dateOfBirth: Date;
    public dateOfEntry: Date;
    public dateOfEndTrial: Date;
    public isOnTrial: boolean;
    public position: string;
    public numberOfChildren: number;
    public otherAbsenceEnt: string;
    public group: Group;
    public role: string;
    public absences: Absence[];
    
    //TODO for now, added some optional variables to constructor
    constructor(
        firstName?: string, lastName?: string, email?: string, dateOfBirth?: Date, dateOfEntry?: Date, position?: string, 
        numberOfChildren?: number, group?: Group) {
        
        super();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
        this.dateOfEntry = dateOfEntry;
        this.position = position;
        this.numberOfChildren = numberOfChildren;
        this.group = group;
    }
}














