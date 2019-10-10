import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';


const routes: Routes = [
  {path: "admin/absence-index", component: AbsencesIndexComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
