import { Component, OnInit } from '@angular/core';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { ActivatedRoute } from '@angular/router';
import { Group } from 'src/app/models/Group.model';

@Component({
  selector: 'app-admin-groups',
  templateUrl: './admin-groups.component.html',
  styleUrls: ['./admin-groups.component.css']
})
export class AdminGroupsComponent implements OnInit {
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
