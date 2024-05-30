import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { AdminTemplateComponent } from './admin-template/admin-template.component';
import {MatToolbar, MatToolbarModule} from "@angular/material/toolbar";
import {MatButton, MatButtonModule, MatIconButton} from "@angular/material/button";
import {MatIcon, MatIconModule} from "@angular/material/icon";
import {MatMenu, MatMenuModule, MatMenuTrigger} from "@angular/material/menu";
import {MatSidenav, MatSidenavModule} from "@angular/material/sidenav";
import {MatListModule} from "@angular/material/list";
import {MatCard, MatCardContent, MatCardHeader, MatCardModule} from "@angular/material/card";
import {MatFormField, MatFormFieldModule} from "@angular/material/form-field";
import {MatInput, MatInputModule} from "@angular/material/input";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {HttpClientModule} from "@angular/common/http";
import {MatTableModule} from "@angular/material/table";
import {MatPaginator, MatPaginatorModule} from "@angular/material/paginator";
import {MatSortModule} from "@angular/material/sort";
import {MatDatepickerInput, MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {MatSelect, MatSelectModule} from "@angular/material/select";
import {MatProgressSpinner, MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { ProfileComponent } from './profile/profile.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ComposantsComponent } from './composants/composants.component';
import { HistoriquesComponent } from './historiques/historiques.component';
import { LoadComposantsComponent } from './load-composants/load-composants.component';
import { LoadHistoriquesComponent } from './load-historiques/load-historiques.component';
import {AuthGuard} from "./guards/auth.guard";
import {AuthorizationGuard} from "./guards/AuthorizationGuard";
import { ComposantDetailsComponent } from './composant-details/composant-details.component';
import {MatGridListModule} from "@angular/material/grid-list";
import { AreaComponent } from './shared/widgets/area/area.component';
import {HighchartsChartModule} from "highcharts-angular";
import { CardComponent } from './shared/widgets/card/card.component';
import { NewComposantComponent } from './new-composant/new-composant.component';
import {MatTooltip, MatTooltipModule} from "@angular/material/tooltip";
import { MachinesComponent } from './machines/machines.component';
import { MachineDetailsComponent } from './machine-details/machine-details.component';
import {MatDialogContent} from "@angular/material/dialog";
import {NgxMatDatetimePickerModule, NgxMatTimepickerModule} from "@angular-material-components/datetime-picker";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ComposantsSettingComponent } from './composants-setting/composants-setting.component';
import {MatExpansionModule, MatExpansionPanel} from "@angular/material/expansion";
import { ManageStatusComponent } from './manage-status/manage-status.component';
import { ManageTypeMachineComponent } from './manage-type-machine/manage-type-machine.component';
import { ManageModulesComponent } from './manage-modules/manage-modules.component';

let MatDialogContentModule;

// @ts-ignore
// @ts-ignore
// @ts-ignore
@NgModule({
  declarations: [

    AppComponent,
    AdminTemplateComponent,
    ProfileComponent,
    HomeComponent,
    LoginComponent,
    DashboardComponent,
    ComposantsComponent,
    HistoriquesComponent,
    LoadComposantsComponent,
    LoadHistoriquesComponent,
    ComposantDetailsComponent,
    AreaComponent,
    CardComponent,
    NewComposantComponent,
    MachinesComponent,
    MachineDetailsComponent,
    ComposantsSettingComponent,
    ManageStatusComponent,
    ManageTypeMachineComponent,
    ManageModulesComponent
  ],
    imports: [
        BrowserModule,
        HttpClientModule,
        BrowserAnimationsModule,
        MatDatepickerModule,
        MatInputModule,
        NgxMatTimepickerModule,
        FormsModule,
        ReactiveFormsModule,
        MatButtonModule,
        NgxMatDatetimePickerModule,
        BrowserModule,
        AppRoutingModule,
        MatToolbarModule,
        MatIconModule,
        MatIconModule,
        MatButtonModule,
        MatMenuModule,
        MatSidenavModule,
        MatListModule,
        MatCardModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule,
        HttpClientModule,
        MatTableModule,
        MatPaginatorModule,
        MatSortModule,
        MatDatepickerModule, MatNativeDateModule, MatSelectModule,
        MatProgressSpinnerModule,
        MatGridListModule, MatDatepickerModule, MatNativeDateModule, MatSelectModule,
        MatProgressSpinnerModule,
        HighchartsChartModule, MatTooltipModule, MatDialogContent, NgxMatTimepickerModule,MatExpansionModule
    ],
  providers: [
    provideAnimationsAsync(), AuthGuard,AuthorizationGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
