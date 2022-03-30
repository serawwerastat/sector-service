import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {PageNotFoundComponent} from "./modules/page-not-found/page-not-found.component";

const routes: Routes = [
  {path: '', redirectTo: '/sector', pathMatch: 'full'},
  {path: 'sector', loadChildren: () => import('./modules/sector/sector.module').then(m => m.SectorModule)},
  {path: '**', component: PageNotFoundComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
