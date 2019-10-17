import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AdminUserShowComponent } from "./components/admin/user-index/admin-user-index/admin-user-show.component";
import { AbsencesIndexComponent } from "./components/admin/absences-index/absences-index.component";
import { LoginComponent } from "./components/login/login.component";
import { EmpAbsencesIndexComponent } from "./components/employee/emp-absences-index/emp-absences-index.component";
import { AbsencesCreateComponent } from "./components/employee/absences-create/absences-create.component";
import { GetEmployeeAbsences } from "./resolvers/GetEmployeeAbsences";

const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  { path: "admin/absence-index", component: AbsencesIndexComponent },
  { path: "admin/user-index", component: AdminUserShowComponent },
  { path: "employee/absence-index", component: EmpAbsencesIndexComponent },
  {
    path: "employee/absence-create",
    component: AbsencesCreateComponent,
    resolve: { absences: GetEmployeeAbsences }
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
