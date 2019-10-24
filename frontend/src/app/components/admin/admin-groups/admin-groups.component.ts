import { Component, OnInit, OnDestroy } from '@angular/core';
import { ApiCommunicationService } from 'src/app/services/api-communication.service';
import { ActivatedRoute } from '@angular/router';
import { Group } from 'src/app/models/Group.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-admin-groups',
  templateUrl: './admin-groups.component.html',
  styleUrls: ['./admin-groups.component.css']
})
export class AdminGroupsComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = ['name', 'parent', 'leaders', 'employees'];
  dataSource: any;
  error: string;
  private _unsubscribe$ = new Subject<void>();

  constructor(private api: ApiCommunicationService, private activatedRoute: ActivatedRoute) {
    this.activatedRoute.data.pipe(takeUntil(this._unsubscribe$)).subscribe((data) => {
      this.dataSource = data.groupResolver;
      this.dataSource.forEach(element => {
        if (element.parentId) {
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

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete(); 
  }

}
