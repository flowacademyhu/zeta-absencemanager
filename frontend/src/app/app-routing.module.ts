import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminUserShowComponent } from './components/admin/user-index/admin-user-index/admin-user-show.component';
import { AdminAbsencesIndexComponent } from './components/admin/absences-index/admin-absences-index.component';
import { LoginComponent } from './components/login/login.component';
import { AdminUserEditDestroyShowComponent } from './components/admin/user-edit-destroy-show/admin-user-edit-destroy-show/admin-user-edit-destroy-show.component';
import { UserResolver } from './UserResolver';

import { AbsencesCreateComponent } from "./components/employee/absences-create/absences-create.component";
import { userprofileupdateresolver } from './userprofileupdateresolver';

import { EmployeeShowComponent } from './components/employee/employee-show/employee-show.component';
import { EmpAbsencesIndexComponent } from "./components/employee/emp-absences-index/emp-absences-index.component";
import { GetEmployeeAbsencesResolver } from "./resolvers/GetEmployeeAbsencesResolver";
import { AdminAbsenceResolver } from "./components/resolvers/AdminAbsenceResolver";
import { GroupIndexComponent } from "./components/admin/group-index/group-index.component";
import { GroupResolver } from "./resolvers/GroupResolver";
import { AbsenceShowEditComponent } from "./components/employee/absence-show-edit/absence-show-edit.component";

const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  { path: "admin/absence-index", component: AdminAbsencesIndexComponent },
  { path: 'admin/user-index', component: AdminUserShowComponent },
  { path: "admin/user-index", component: AdminUserShowComponent },
  { path: "employee/absence-create", component: AbsencesCreateComponent },
  { path: "admin/absence-index", component: AdminAbsencesIndexComponent },
  { path: 'admin/user-index', component: AdminUserShowComponent },
  { path: "admin/user-esd", component: AdminUserEditDestroyShowComponent, resolve: { userList: UserResolver }},
  { path: "employee/absence-create", component: AbsencesCreateComponent },
  { path: 'profile', component: EmployeeShowComponent },
  {
    path: "employee/absence-index",
    component: EmpAbsencesIndexComponent,
    resolve: { absences: GetEmployeeAbsencesResolver }
  },
  {
    path: "admin/absence-index",
    component: AdminAbsencesIndexComponent,
    resolve: { adminAbsenceList: AdminAbsenceResolver }
  },
  { path: "admin/user-index", component: AdminUserShowComponent },
  {
    path: "admin/user-esd",
    component: AdminUserEditDestroyShowComponent,
    resolve: { userList: UserResolver }
  },
  {
    path: "employee/absence-create",
    component: AbsencesCreateComponent
  },
  {
    path: "employee/absence-show-edit/:id",
    component: AbsenceShowEditComponent
  },
  {
    path: "admin/group/index",
    component: GroupIndexComponent,
    resolve: { groupResolver: GroupResolver }
  }
];


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [GroupResolver, UserResolver, GetEmployeeAbsencesResolver, AdminAbsenceResolver]
})
export class AppRoutingModule {}
