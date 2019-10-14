import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminUserShowComponent } from './components/admin/user-index/admin-user-index/admin-user-show.component';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';
import { LoginComponent } from './components/login/login.component';
import { AbsenceIndexComponent } from './components/employee/emp-absence-index/absence-index.component';
import { AdminUserEditDestroyShowComponent } from './components/admin/user-edit-destroy-show/admin-user-edit-destroy-show/admin-user-edit-destroy-show.component';


const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  { path: "admin/absence-index", component: AbsencesIndexComponent },
  { path: 'admin/user-index', component: AdminUserShowComponent },
  { path: "user/absence-index", component: AbsenceIndexComponent },
  { path: "admin/user-esd", component: AdminUserEditDestroyShowComponent}
]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
