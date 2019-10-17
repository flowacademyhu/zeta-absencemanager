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
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent implements OnInit {
  private createAbsenceForm: FormGroup;
  private types;
  private error: string;

  enumSelector(definition) {
    return Object.keys(definition).map(key => ({
      value: key,
      title: definition[key]
    }));
  }

  constructor(
    private api: ApiCommunicationService,
    private dialogRef: MatDialogRef<GroupCreateComponent>,
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