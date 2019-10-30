import { Component, OnInit, OnDestroy, ViewChild } from "@angular/core";
import { MatDialog, MatDialogConfig } from "@angular/material/dialog";
import { EmployeeAbsenceCreateModalComponent } from "../modals/employee-absence-create-modal/employee-absence-create-modal.component";
import { ActivatedRoute, Router } from "@angular/router";
import { takeUntil } from "rxjs/operators";
import { Subject } from "rxjs";
import { Absence } from "src/app/models/Absence.model";
import { EmployeeAbsenceEditModalComponent } from "../modals/employee-absence-edit-modal/employee-absence-edit-modal.component";
import { MatPaginator, PageEvent } from '@angular/material';
import { ExcelService } from 'src/app/services/excel.service';

@Component({
  selector: "app-employee-absences",
  templateUrl: "./employee-absences.component.html",
  styleUrls: ["./employee-absences.component.css"]
})
export class EmployeeAbsencesComponent implements OnInit, OnDestroy {
  displayedColumns: string[] = [
    "id",
    "begin",
    "end",
    "days",
    "type",
    "summary",
    "status",
    "edit"
  ];
  private _unsubscribe$: Subject<boolean> = new Subject<boolean>();
  absences: Absence[];
  @ViewChild(MatPaginator, { static: false }) paginator: MatPaginator;
  public length = 0;
  public pageIndex = 0;
  public pageSize = 5; 

  constructor(
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private router: Router,
    private excelService: ExcelService
    ) {}

  ngOnInit() {
    this.route.data
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe((data: any) => {
        this.absences = data.absences.embedded;
        this.pageSize = data.absences.metadata.pageSize;
        this.pageIndex = data.absences.metadata.pageIndex;
        this.length = data.absences.metadata.totalElements;
      });
  }

  openDialog() {
    const dialogConfig = new MatDialogConfig();

    dialogConfig.disableClose = true;
    dialogConfig.autoFocus = true;
    dialogConfig.minWidth = "40vw";

    dialogConfig.data = {};

    const dialogRef = this.dialog.open(
      EmployeeAbsenceCreateModalComponent,
      dialogConfig
    );

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(() => this.router.navigateByUrl(this.router.url));
  }

  editAbsence(id: number): void {
    const dialogRef = this.dialog.open(EmployeeAbsenceEditModalComponent, {
      data: { absence: this.absences.filter(absence => absence.id === id)[0] }
    });

    dialogRef
      .afterClosed()
      .pipe(takeUntil(this._unsubscribe$))
      .subscribe(() => this.router.navigateByUrl(this.router.url));
  }

  ngOnDestroy(): void {
    this._unsubscribe$.next();
    this._unsubscribe$.complete();
  }

  public onPageChange(event: PageEvent) {
    this.router.navigate(["absences"], {
      queryParams: { 
        page: event.pageIndex,
        size: event.pageSize
      }
    })
  }

  exportAsXLSX(): void {
    this.excelService.exportAsExcelFile(this.absences, 'sample');
  }
}
