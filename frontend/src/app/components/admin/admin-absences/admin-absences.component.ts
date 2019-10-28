import { Component, OnInit, ViewChild, AfterViewInit, Input } from "@angular/core";
import { Absence } from "src/app/models/Absence.model";
import { ApiCommunicationService } from "src/app/services/api-communication.service";
import { ActivatedRoute } from "@angular/router";
import { Subject } from "rxjs";
import { takeUntil, tap } from "rxjs/operators";
import { MatDialogConfig, MatDialog } from "@angular/material/dialog";
import { AdminAbsenceCreateModalComponent } from "../modals/admin-absence-create-modal/admin-absence-create-modal.component";
import { AdminAbsenceEditModalComponent } from "../modals/admin-absence-edit-modal/admin-absence-edit-modal.component";
import { MatPaginator, MatTableDataSource } from '@angular/material';

@Component({
  selector: "app-admin-absences",
  templateUrl: "./admin-absences.component.html",
  styleUrls: ["./admin-absences.component.css"]
})
export class AdminAbsencesComponent implements OnInit, AfterViewInit {
  private _unsubscribe$: Subject<boolean> = new Subject<boolean>();
  displayedColumns: string[] = [
    "id",
    "begin",
    "end",
    "days",
    "type",
    "status",
    "created_at",
    "reporter",
    "assignee",
    "edit"
  ];
  absencesList: Absence[];

  length = 5;
  dataSource: MatTableDataSource<Absence>;
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;

  constructor(
    public api: ApiCommunicationService,
    private route: ActivatedRoute,
    private dialog: MatDialog
  ) { }

  ngOnInit() {
    /*     this.route.data
          .pipe(takeUntil(this._unsubscribe$))
          .subscribe((absences: any) => {
            this.absencesList = absences.adminAbsenceList;
            console.log(this.absencesList);
          }); */
    this.api.adminAbsence().getAbsencesPage(3, 0).subscribe(data => {
      this.dataSource = new MatTableDataSource<Absence>(data);
      this.dataSource.paginator = this.paginator;
      // this.absencesList = data.content
    });
  }

  ngAfterViewInit() {
    this.api.adminAbsence().getAbsencesPage(this.paginator.pageSize, this.paginator.pageIndex).subscribe(data => {

      this.paginator.length = data.totalElements;
      this.paginator.pageSize = data.size;
      this.paginator.pageIndex = data.number;
    })
  }

  /*   loadPage() {
      this.api.adminAbsence().getAbsencesPage(this.paginator.pageSize, this.paginator.pageIndex).subscribe(data => {
        console.log(data);
        this.absencesList = data.content;
        this.dataSource = new MatTableDataSource<Absence>(data.content);
        this.dataSource.paginator = this.paginator;
        this.dataSource.paginator.length = data.totalElements;
        this.length = data.totalElements;
        console.log(data.totalElements + "<- TotalElements");
        // this.paginator.length = data.totalPages;
      })
    } */

  /*   handlePage(event) {
      this.api.adminAbsence().getAbsencesPage(event.pageSize, event.pageIndex).subscribe(data => {
        console.log(data);
        this.absencesList = data.content;
      })
    } */

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40vw";

    dialogConfig.data = {};

    const dialogRef = this.dialog.open(
      AdminAbsenceCreateModalComponent,
      dialogConfig
    );

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(data2 => {
        this.api
          .adminAbsence()
          .getAbsences()
          .subscribe(data => {
            this.absencesList = data;
          });
      });
  }

  editAbsence(id: number): void {
    const dialogRef = this.dialog.open(AdminAbsenceEditModalComponent, {
      data: {
        absence: this.absencesList.filter(absence => absence.id === id)[0]
      }
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(data2 => {
        this.api
          .adminAbsence()
          .getAbsences()
          .subscribe(data => {
            this.absencesList = data;
          });
      });
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }
}
