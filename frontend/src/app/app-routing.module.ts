import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AdminUserShowComponent } from './components/admin/user-index/admin-user-index/admin-user-show.component';
import { AbsencesIndexComponent } from './components/admin/absences-index/absences-index.component';
import { LoginComponent } from './components/login/login.component';
import { GroupIndexComponent } from './components/admin/group-index/group-index.component';
import { GroupResolverService } from './resolvers/group-resolver.service';


const routes: Routes = [
  { path: "", component: LoginComponent },
  { path: "login", component: LoginComponent },
  { path: "admin/absence-index", component: AbsencesIndexComponent },
  { path: 'admin/user-index', component: AdminUserShowComponent },
  { path: 'admin/group/index', component: GroupIndexComponent, resolve: { groupResolver: GroupResolverService } }
]


@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [GroupResolverService]
})
export class AppRoutingModule { }
