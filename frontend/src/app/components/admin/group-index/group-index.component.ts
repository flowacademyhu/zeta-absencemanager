import { Component, OnInit } from '@angular/core';
import { ApiCommunicationService } from 'src/app/services/ApiCommunication.service';
import { ActivatedRoute } from '@angular/router';
import { Group } from 'src/app/models/Group.model';

@Component({
  selector: 'app-group-index',
  templateUrl: './group-index.component.html',
  styleUrls: ['./group-index.component.css']
})
export class GroupIndexComponent implements OnInit {
  displayedColumns: string[] = ['name', 'parent', 'leaders', 'employees'];
  dataSource: any;
  error: string;

  constructor(private api: ApiCommunicationService, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.data.subscribe((data) => {
      this.dataSource = data.groupResolver;
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

  ngOnInit() { }
}
