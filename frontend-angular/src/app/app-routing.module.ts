import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {LoginComponent} from "./login/login.component";
import {AdminTemplateComponent} from "./admin-template/admin-template.component";
import {HomeComponent} from "./home/home.component";
import {ProfileComponent} from "./profile/profile.component";
import {DashboardComponent} from "./dashboard/dashboard.component";
import {ComposantsComponent} from "./composants/composants.component";
import {HistoriquesComponent} from "./historiques/historiques.component";
import {LoadComposantsComponent} from "./load-composants/load-composants.component";
import {LoadHistoriquesComponent} from "./load-historiques/load-historiques.component";
import {AuthGuard} from "./guards/auth.guard";
import {AuthorizationGuard} from "./guards/AuthorizationGuard";
import {ComposantDetailsComponent} from "./composant-details/composant-details.component";
import {NewComposantComponent} from "./new-composant/new-composant.component";
import {MachinesComponent} from "./machines/machines.component";
import {MachineDetailsComponent} from "./machine-details/machine-details.component";
import {ManageStatusComponent} from "./manage-status/manage-status.component";
import {ManageTypeMachineComponent} from "./manage-type-machine/manage-type-machine.component";
import {ManageModulesComponent} from "./manage-modules/manage-modules.component";

const routes: Routes = [
  {path: "", component: LoginComponent},
  {path: "login", component: LoginComponent},
  {path: "admin", component: AdminTemplateComponent,
    canActivate:[AuthGuard],
    children:[
      {path: "home", component: HomeComponent},
      {path: "profile", component: ProfileComponent},
      {path: "dashboard", component: DashboardComponent},
      {path: "composants", component: ComposantsComponent},
      {path: "historiques", component: HistoriquesComponent},
      {path: "machines", component: MachinesComponent},
      {path: "machine-details/:machineId", component: MachineDetailsComponent},
      {path: "composant-details/:composantId", component: ComposantDetailsComponent},
      {
        path: "loadComposants", component: LoadComposantsComponent,
        canActivate :[AuthorizationGuard],data:{roles:['ADMIN']}
      },
      {
        path: "loadHistoriques", component: LoadHistoriquesComponent,
        canActivate :[AuthorizationGuard],data:{roles:['ADMIN']}
      },
      {path: "new-composant", component: NewComposantComponent},
      {path: "manageStatus", component: ManageStatusComponent},
      {path: "manageTypeMachine", component: ManageTypeMachineComponent},
      {path: "manageModules", component: ManageModulesComponent}

    ]},


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
