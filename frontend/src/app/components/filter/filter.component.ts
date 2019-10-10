import { Component, OnInit, Input } from '@angular/core';
import { MatTableDataSource, MatFormFieldControl } from '@angular/material';


@Component({
  selector: 'app-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css']
})
export class FilterComponent implements OnInit {

  @Input() dataSource;


  constructor() { }

  ngOnInit() {
  }

  applyFilter(filterValue: string){
    this.dataSource.filter = filterValue.trim().toLocaleLowerCase();
  //MatTableDataSource class has a property, called filter, assign a string to that property
  }

}
