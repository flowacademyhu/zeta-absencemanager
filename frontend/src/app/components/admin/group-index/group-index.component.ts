import { Component, OnInit } from '@angular/core';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';

@Component({
  selector: 'app-group-index',
  templateUrl: './group-index.component.html',
  styleUrls: ['./group-index.component.css']
})
export class GroupIndexComponent implements OnInit {
  displayedColumns: string[] = ['name', 'parentId', 'parent', 'leaders', 'employees'];
  dataSource: any;
  error: string;

  constructor(private apiCommunicationService: ApiCommunicationService) { }

  ngOnInit() {
    this.apiCommunicationService.group().getGroups().subscribe((data) => {
      console.log(data);
      this.dataSource = data;
      this.dataSource.forEach(element => {
        if (element.parentId !== null) {
          this.dataSource.forEach(group => {
            if (element.parentId === group.id) {
              element.parent = group.name;
            }
          });
        }
      });
    }, (error) => {
      this.error = error;
    })
  }
}
