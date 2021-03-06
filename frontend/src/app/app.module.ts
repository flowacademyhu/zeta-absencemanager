import { BrowserAnimationsModule } from "@angular/platform-browser/animations";
import { CommonModule, DatePipe } from "@angular/common";
import { AppRoutingModule } from "./app-routing.module";
import { BrowserModule } from "@angular/platform-browser";
import { FormsModule, ReactiveFormsModule } from "@angular/forms";
import { MatMenuModule } from "@angular/material/menu";
import { NgModule, APP_INITIALIZER } from "@angular/core";
import { MatPaginatorModule, MAT_DATE_LOCALE } from "@angular/material";
import { HttpClientModule } from "@angular/common/http";

import {
  HttpInterceptor,
  HttpRequest,
  HttpHandler
} from "@angular/common/http";
import {
  MatToolbarModule,
  MatNativeDateModule,
  MatIconModule,
  MatSidenavModule,
  MatListModule,
  MatButtonModule,
  MatGridListModule,
  MatInputModule,
  MatCardModule,
  MatTableModule,
  MatFormFieldModule,
  MatDialogModule,
  MatDatepickerModule,
  MatDialogTitle,
  MatDialogRef,
  MAT_DIALOG_DATA,
  MatSelectModule,
  MatSortModule,
  MatProgressSpinnerModule,
  MatCheckboxModule
} from "@angular/material/";

import { HTTP_INTERCEPTORS } from "@angular/common/http";
import { TokenInterceptor } from "./interceptors/token.interceptor";
import { AbsenceTypePipe } from "./pipes/absence-type-pipe";
import { AbsenceStatusPipe } from "./pipes/absence-status-pipe";
import { UserRolePipe } from "./pipes/user-role-pipe";

//Own Components

import { LoginComponent } from "./components/common/login/login.component";
import { SessionService } from "./services/session.service";
import { EmployeeProfileComponent } from "./components/employee/employee-profile/employee-profile.component";
import { ApiCommunicationService } from "./services/api-communication.service";
import { UserService } from "./services/user.service";
import { AppComponent } from "./app.component";
import { HeaderComponent } from "./components/common/header/header.component";
import { FooterComponent } from "./components/common/footer/footer.component";
import { ContentComponent } from "./components/common/content/content.component";
import { AdminUsersComponent } from "./components/admin/admin-users/admin-users.component";
import { AdminAbsencesComponent } from "./components/admin/admin-absences/admin-absences.component";
import { EmployeeAbsencesComponent } from "./components/employee/employee-absences/employee-absences.component";
import { EmployeeAbsenceCreateModalComponent } from "./components/employee/modals/employee-absence-create-modal/employee-absence-create-modal.component";
import { UserResolver } from "./resolvers/UserResolver";
import { AdminUserEditModalComponent } from "./components/admin/modals/admin-user-edit-modal/admin-user-edit-modal.component";
import { EmployeeShowResolver } from "src/app/resolvers/EmployeeShowResolver";
import { AdminGroupsComponent } from "./components/admin/admin-groups/admin-groups.component";
import { AdminUserAddModalComponent } from "./components/admin/modals/admin-user-add-modal/admin-user-add-modal.component";
import { EmployeeAbsenceEditModalComponent } from "./components/employee/modals/employee-absence-edit-modal/employee-absence-edit-modal.component";
import { AdminAbsenceResolver } from "./resolvers/AdminAbsenceResolver";
import { GroupResolver } from "./resolvers/GroupResolver";
import { GetEmployeeAbsencesResolver } from "./resolvers/GetEmployeeAbsencesResolver";
import { AuthGuard } from "./guards/auth.guard";
import { userSessionStarterFactory } from "./utils/UserSessionStarterFactory";
import { ChangePasswModalComponent } from "./components/employee/modals/change-passw-modal/change-passw-modal.component";
import { AdminGuard } from "./guards/admin.guard";
import { AdminGroupEditModalComponent } from "./components/admin/modals/admin-group-edit-modal/admin-group-edit-modal.component";
import { AdminAbsenceCreateModalComponent } from "./components/admin/modals/admin-absence-create-modal/admin-absence-create-modal.component";
import { AdminAbsenceEditModalComponent } from "./components/admin/modals/admin-absence-edit-modal/admin-absence-edit-modal.component";
import { AdminUserDeleteModalComponent } from "./components/admin/modals/admin-user-delete-modal/admin-user-delete-modal.component";
import { AdminGroupDeleteModalComponent } from "./components/admin/modals/admin-group-delete-modal/admin-group-delete-modal.component";
import { EmployeeProfileDeleteModalComponent } from "./components/employee/modals/employee-profile-delete-modal/employee-profile-delete-modal.component";
import { EmployeeProfileEditModalComponent } from "./components/employee/modals/employee-profile-edit-modal/employee-profile-edit-modal.component";
import { AdminGroupCreateModalComponent } from "./components/admin/modals/admin-group-create-modal/admin-group-create-modal.component";
import { DateFormingService } from "./services/date-forming.service";

@NgModule({
  declarations: [
    AppComponent,
    //commons
    HeaderComponent,
    FooterComponent,
    ContentComponent,
    LoginComponent,
    //admin
    AdminAbsencesComponent,
    AdminUsersComponent,
    AdminGroupsComponent,
    AdminUserAddModalComponent,
    AdminUserEditModalComponent,
    AdminUserDeleteModalComponent,
    AdminGroupCreateModalComponent,
    AdminGroupEditModalComponent,
    ChangePasswModalComponent,
    AdminGroupDeleteModalComponent,
    AdminAbsenceCreateModalComponent,
    AdminAbsenceEditModalComponent,
    //employee
    EmployeeProfileComponent,
    EmployeeAbsencesComponent,
    EmployeeAbsenceCreateModalComponent,
    EmployeeAbsenceEditModalComponent,
    EmployeeProfileDeleteModalComponent,
    EmployeeProfileEditModalComponent,
    //pipe
    AbsenceTypePipe,
    AbsenceStatusPipe,
    UserRolePipe
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatToolbarModule,
    MatSidenavModule,
    MatListModule,
    MatButtonModule,
    MatIconModule,
    MatGridListModule,
    MatCardModule,
    MatTableModule,
    MatInputModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatNativeDateModule,
    MatInputModule,
    MatMenuModule,
    MatFormFieldModule,
    MatFormFieldModule,
    MatDialogModule,
    MatPaginatorModule,
    MatDatepickerModule,
    MatSelectModule,
    MatSortModule,
    MatProgressSpinnerModule,
    CommonModule,
    MatCheckboxModule
  ],
  providers: [
    EmployeeShowResolver,
    AdminAbsenceResolver,
    GroupResolver,
    GetEmployeeAbsencesResolver,
    UserResolver,
    SessionService,
    ApiCommunicationService,
    UserService,
    DateFormingService,
    AuthGuard,
    AdminGuard,
    DatePipe,
    {
      provide: APP_INITIALIZER,
      useFactory: userSessionStarterFactory,
      deps: [SessionService],
      multi: true
    },
    { provide: MAT_DATE_LOCALE, useValue: "hun" },
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    },
    { provide: MatDialogTitle, useValue: {} },
    { provide: MatDialogRef, useValue: {} },
    { provide: MAT_DIALOG_DATA, useValue: [] }
  ],
  bootstrap: [AppComponent],
  entryComponents: [
    //employee modals
    EmployeeAbsenceCreateModalComponent,
    EmployeeAbsenceEditModalComponent,
    EmployeeProfileEditModalComponent,
    //admin modals
    AdminUserEditModalComponent,
    AdminUserAddModalComponent,
    AdminGroupEditModalComponent,
    ChangePasswModalComponent,
    AdminGroupCreateModalComponent,
    ChangePasswModalComponent,
    AdminAbsenceEditModalComponent,
    AdminAbsenceCreateModalComponent,
    AdminUserDeleteModalComponent,
    AdminGroupDeleteModalComponent,
    EmployeeProfileDeleteModalComponent
  ]
})
export class AppModule {}
