import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';
import { LoginComponent } from './components/login/login.component';


const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "login", component: LoginComponent},
  {path: "admin/absence-index", component: AbsencesIndexComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
