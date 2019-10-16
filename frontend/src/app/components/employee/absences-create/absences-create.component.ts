import { Component, OnInit, Inject } from "@angular/core";
import { FormGroup, FormControl, Validators } from "@angular/forms";
import { Absence, AbsenceType } from "src/app/models/Absence.model";
import {
  MatDialog,
  MatDialogRef,
  MAT_DIALOG_DATA
} from "@angular/material/dialog";

@Component({
  selector: "app-absences-create",
  templateUrl: "./absences-create.component.html",
  styleUrls: ["./absences-create.component.css"]
})
export class AbsencesCreateComponent implements OnInit {
  private createAbsenceForm: FormGroup;
  private types;

  enumSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  }

  constructor(
    private dialogRef: MatDialogRef<AbsencesCreateComponent>,
    @Inject(MAT_DIALOG_DATA) private data: any
  ) {}

  ngOnInit() {
    this.types = this.enumSelector(AbsenceType);
    this.createAbsenceForm = new FormGroup({
      type: new FormControl("", Validators.required),
      summary: new FormControl(""),
      start: new FormControl("", Validators.required),
      end: new FormControl("", Validators.required)
    });
  }

  public onSubmit(createAbsenceFormValue): void {
    if (this.createAbsenceForm.valid) {
      let newAbsence = new Absence(
        createAbsenceFormValue.type,
        createAbsenceFormValue.summary,
        createAbsenceFormValue.start,
        createAbsenceFormValue.end
      );

      console.log(newAbsence);
    }
  }

  public onCancel(): void {
    this.dialogRef.close();
  }
}
