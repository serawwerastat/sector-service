import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import * as fromApp from "../../store/app.reducer";
import {Store} from "@ngrx/store";
import * as SectorActions from "./store/sector.action";
import {SectorDataModel} from "./models/sectorData.model";
import {Observable, Subscription} from "rxjs";
import {Sector} from "./store/sector.reducer";

@Component({
  selector: 'app-sector',
  templateUrl: './sector.component.html',
  styleUrls: ['./sector.component.css']
})
export class SectorComponent implements OnInit, OnDestroy {
  sectorInputForm: FormGroup;
  userSectorData: SectorDataModel;
  sectors: Observable<{ sectors: Sector[] }>;
  isLoading = false;
  error = null;

  sectorSub: Subscription;

  constructor(
    private store: Store<fromApp.AppState>
  ) {
  }

  ngOnInit(): void {
    this.sectors = this.store.select('sector');
    this.sectorSub = this.store.select('sector')
      .subscribe(sectorState => {
        this.isLoading = sectorState.isLoading;
        this.error = sectorState.error;

        this.userSectorData = sectorState.userSector;
        if (this.userSectorData && this.sectorInputForm) {
          this.sectorInputForm.patchValue({
            name: this.userSectorData.name,
            sector: this.userSectorData.sectors,
            agreement: this.userSectorData.agreementAccepted
          })
        }
      })
    this.initForm();
  }

  initForm() {
    this.sectorInputForm = new FormGroup({
      name: new FormControl(this.userSectorData?.name, [Validators.required, Validators.minLength(3)]),
      sector: new FormControl(this.userSectorData?.sectors, [Validators.required]),
      agreement: new FormControl(this.userSectorData, [Validators.requiredTrue])
    })
  }

  onSubmit() {
    let name = this.sectorInputForm.value.name;
    let sector = this.sectorInputForm.value.sector;
    let agreement = this.sectorInputForm.value.agreement;

    let sectorData = new SectorDataModel(name, sector, this.userSectorData?.userId, agreement)
    this.store.dispatch(SectorActions.startSaving({sectorData}))
    this.sectorInputForm.reset();
  }

  isInvalidInput(controller: string) {
    return !this.sectorInputForm.get(controller)?.valid && this.sectorInputForm.get(controller)?.touched
  }

  onHandleError() {
    this.store.dispatch(SectorActions.clearError())
  }

  ngOnDestroy() {
    this.sectorSub.unsubscribe();
  }
}
