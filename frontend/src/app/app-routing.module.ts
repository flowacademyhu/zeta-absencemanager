import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminUserShowComponent } from './components/admin/user-index/admin-user-index/admin-user-show.component';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';
import { AbsenceCreateComponent } from './components/admin/absence-create/absence-create.component';
import { LoginComponent } from './components/login/login.component';


const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  { path: 'admin/user-index', component: AdminUserShowComponent },
  { path: "admin/absence-index", component: AbsencesIndexComponent },
  { path: "admin/absence-create", component: AbsenceCreateComponent }
]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
