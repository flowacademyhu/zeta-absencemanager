import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminUserShowComponent } from './components/admin/user-index/admin-user-index/admin-user-show.component';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';


const routes: Routes = [
 { path: 'admin/user-index', component: AdminUserShowComponent },
 { path: "admin/absence-index", component: AbsencesIndexComponent } 
]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
