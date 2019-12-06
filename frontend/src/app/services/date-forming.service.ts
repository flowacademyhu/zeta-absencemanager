import { Injectable } from '@angular/core';

@Injectable()
export class DateFormingService {

  constructor() { }

  private getDate(date) {
    let formatedDate = [];
    for (let i = 0; i < 3; i++) {
      formatedDate.push(date[i]);
    }
    return formatedDate;
  }
}