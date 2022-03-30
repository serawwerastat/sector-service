import {NgModule} from "@angular/core";
import {RouterModule} from "@angular/router";
import {SectorComponent} from "./sector.component";
import {SectorResolverService} from "./service/sector-resolver.service";

const sectorRoutes = [
  {
    path: '',
    component: SectorComponent,
    resolve: [SectorResolverService]
  }
]

@NgModule({
  imports: [RouterModule.forChild(sectorRoutes)],
  exports: [RouterModule]
})
export class SectorRoutingModule {

}
