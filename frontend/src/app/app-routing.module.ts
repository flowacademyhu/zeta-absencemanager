import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AdminUserShowComponent } from "./components/admin/user-index/admin-user-index/admin-user-show.component";
import { AdminAbsencesIndexComponent } from "./components/admin/absences-index/admin-absences-index.component";
import { LoginComponent } from "./components/login/login.component";
import { UserAbsenceIndexComponent } from "./components/employee/emp-absence-index/user-absence-index.component";
import { AdminUserEditDestroyShowComponent } from "./components/admin/user-edit-destroy-show/admin-user-edit-destroy-show/admin-user-edit-destroy-show.component";
import { UserResolver } from "./UserResolver";
import { AbsencesCreateComponent } from "./components/employee/absences-create/absences-create.component";
import { AbsenceShowEditComponent } from "./components/employee/absence-show-edit/absence-show-edit.component";

const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  { path: "admin/absence-index", component: AdminAbsencesIndexComponent },
  { path: "admin/user-index", component: AdminUserShowComponent },
  { path: "user/absence-index", component: UserAbsenceIndexComponent },
  {
    path: "admin/user-esd",
    component: AdminUserEditDestroyShowComponent,
    resolve: { userList: UserResolver }
  },
  { path: "employee/absence-create", component: AbsencesCreateComponent },
  {
    path: "employee/absence-show-edit/:id",
    component: AbsenceShowEditComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [UserResolver]
})
export class AppRoutingModule {}
