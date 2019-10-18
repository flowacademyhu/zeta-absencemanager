import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";
import { ApiCommunicationService } from "src/app/services/ApiCommunication.service";

@Component({
  selector: "app-absences-create",
  templateUrl: "./absences-create.component.html",
  styleUrls: ["./absences-create.component.css"]
})
export class AbsencesCreateComponent implements OnInit {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<AbsencesCreateComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.types = Absence.enumSelector(AbsenceType);
    this.createAbsenceForm = new FormGroup({
      type: new FormControl("", Validators.required),
      summary: new FormControl(""),
      start: new FormControl("", Validators.required),
      end: new FormControl("", Validators.required)
    });
  }

  public OnSubmit(createAbsenceFormValue): void {
    if (this.createAbsenceForm.valid) {
      let newAbsence = new Absence(
        createAbsenceFormValue.type,
        createAbsenceFormValue.summary,
        createAbsenceFormValue.start,
        createAbsenceFormValue.end
      );
      this.api
        .absence()
        .createAbsence(newAbsence)
        .subscribe(
          data => {
            this.dialogRef.close();
          },
          err => {
            this.error = err.error.message;
          }
        );
    }
  }

  public onCancel(): void {
    this.dialogRef.close();
  }
}
