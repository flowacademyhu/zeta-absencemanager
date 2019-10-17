import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AdminUserShowComponent } from "./components/admin/user-index/admin-user-index/admin-user-show.component";
import { LoginComponent } from "./components/login/login.component";
import { EmpAbsencesIndexComponent } from "./components/employee/emp-absences-index/emp-absences-index.component";
import { AdminAbsencesIndexComponent } from "./components/admin/absences-index/admin-absences-index.component";
import { UserAbsenceIndexComponent } from "./components/employee/emp-absence-index/user-absence-index.component";
import { AdminUserEditDestroyShowComponent } from "./components/admin/user-edit-destroy-show/admin-user-edit-destroy-show/admin-user-edit-destroy-show.component";
import { UserResolver } from "./UserResolver";
import { AbsencesCreateComponent } from "./components/employee/absences-create/absences-create.component";
import { GetEmployeeAbsences } from "./resolvers/GetEmployeeAbsences";
import { CreateUserComponent } from "./modals/create-user/create-user.component";
import { AdminAbsenceResolver } from "./components/resolvers/AdminAbsenceResolver";

const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  {
    path: "employee/absence-index",
    component: EmpAbsencesIndexComponent,
    resolve: { absences: GetEmployeeAbsences }
  },
  {
    path: "admin/absence-index",
    component: AdminAbsencesIndexComponent,
    resolve: { adminAbsenceList: AdminAbsenceResolver }
  },
  { path: "admin/user-index", component: AdminUserShowComponent },
  { path: "user/absence-index", component: UserAbsenceIndexComponent },
  {
    path: "admin/user-esd",
    component: AdminUserEditDestroyShowComponent,
    resolve: { userList: UserResolver }
  },
  {
    path: "employee/absence-create",
    component: AbsencesCreateComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [UserResolver]
})
export class AppRoutingModule {}
