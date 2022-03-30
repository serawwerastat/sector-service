import {NgModule} from "@angular/core";
import {ReactiveFormsModule} from "@angular/forms";
import {SectorComponent} from "./sector.component";
import {SectorRoutingModule} from "./sector-routing.module";
import {CommonModule} from "@angular/common";
import {SharedModule} from "../../shared/shared.module";
import {MatSelectModule} from "@angular/material/select";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [
    SectorComponent
  ],
  imports: [
    ReactiveFormsModule,
    SectorRoutingModule,
    CommonModule,
    SharedModule,
    MatSelectModule,
    MatCheckboxModule,
    MatButtonModule
  ]
})
export class SectorModule {

}
