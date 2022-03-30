import {NgModule} from "@angular/core";
import {LoadingSpinnerComponent} from "./loading-spinner/loading-spinner.component";
import {AlertComponent} from './alert/alert.component';
import {MatButtonModule} from "@angular/material/button";

@NgModule({
  declarations: [
    LoadingSpinnerComponent,
    AlertComponent
  ],
  imports: [
    MatButtonModule
  ],
  exports: [
    LoadingSpinnerComponent,
    AlertComponent]
})
export class SharedModule {

}
