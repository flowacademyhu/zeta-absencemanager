import { NgModule } from "@angular/core";
import { Routes, RouterModule } from "@angular/router";
import { AdminUsersComponent } from "./components/admin/admin-users/admin-users.component";
import { AdminAbsencesComponent } from "./components/admin/admin-absences/admin-absences.component";
import { LoginComponent } from "./components/common/login/login.component";
import { EmployeeProfileComponent } from "./components/employee/employee-profile/employee-profile.component";
import { EmployeeAbsencesComponent } from "./components/employee/employee-absences/employee-absences.component";
import { UserResolver } from "./resolvers/UserResolver";
import { GetEmployeeAbsencesResolver } from "./resolvers/GetEmployeeAbsencesResolver";
import { AdminAbsenceResolver } from "./resolvers/AdminAbsenceResolver";
import { AdminGroupsComponent } from "./components/admin/admin-groups/admin-groups.component";
import { GroupResolver } from "./resolvers/GroupResolver";
import { AuthGuard } from "./guards/auth.guard";
import { AdminGuard } from "./guards/admin.guard";

const routes: Routes = [
  {
    path: "",
    redirectTo: "absences",
    pathMatch: "full",
    canActivate: [AuthGuard]
  },
  {
    path: "login",
    component: LoginComponent
  },
  {
    path: "profile",
    component: EmployeeProfileComponent,
    canActivate: [AuthGuard]
  },
  {
    path: "absences",
    component: EmployeeAbsencesComponent,
    canActivate: [AuthGuard],
    resolve: {
      absences: GetEmployeeAbsencesResolver
    }
  },

  {
    path: "admin",
    canActivate: [AuthGuard, AdminGuard],
    runGuardsAndResolvers: "always",
    children: [
      {
        path: "absences",
        component: AdminAbsencesComponent,
        resolve: {
          adminAbsenceList: AdminAbsenceResolver
        }
      },
      {
        path: "users",
        component: AdminUsersComponent,
        runGuardsAndResolvers: "always",
        resolve: {
          userResolver: UserResolver
        }
      },
      {
        path: "groups",
        component: AdminGroupsComponent,
        runGuardsAndResolvers: "always",
        resolve: {
          groupResolver: GroupResolver
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    GroupResolver,
    UserResolver,
    GetEmployeeAbsencesResolver,
    AdminAbsenceResolver
  ]
})
export class AppRoutingModule {}
