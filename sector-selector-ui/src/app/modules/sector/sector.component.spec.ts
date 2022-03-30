import {ComponentFixture, TestBed} from '@angular/core/testing';

import {SectorComponent} from './sector.component';
import {AppRoutingModule} from "../../app-routing.module";
import {ReactiveFormsModule} from "@angular/forms";
import {Store, StoreModule} from "@ngrx/store";
import * as fromApp from "../../store/app.reducer";
import {MatSelectModule} from "@angular/material/select";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {By} from "@angular/platform-browser";
import {SectorDataModel} from "./models/sectorData.model";
import {of} from "rxjs";

describe('SectorComponent', () => {
  let component: SectorComponent;
  let fixture: ComponentFixture<SectorComponent>;

  const storeMock = {
    select(any) {
      return of({
        sectors: [{id: '1', value: 'Some value', parent: true, level: 0}],
        userSector: new SectorDataModel('some-name', ['1'], '1', true),
        error: '',
        isLoading: false
      });
    }
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SectorComponent],
      imports: [
        AppRoutingModule,
        ReactiveFormsModule,
        StoreModule.forRoot(fromApp.appReducer),
        MatSelectModule,
        MatCheckboxModule,
        BrowserAnimationsModule
      ],
      providers: [
        {
          provide: Store,
          useValue: storeMock
        }
      ]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should not show input form if loading', () => {
    component.isLoading = true;
    fixture.detectChanges();

    let compiled = fixture.nativeElement;
    expect(compiled.querySelector('app-loading-spinner')).toBeTruthy();
    expect(compiled.querySelector('#inputForm')).toBeFalsy();
  });

  it('should have error component if error is present', () => {
    let errorMsg = 'error msg';
    component.error = errorMsg;
    fixture.detectChanges();

    let compiled = fixture.nativeElement;
    expect(compiled.querySelector('app-alert')).toBeTruthy();

    let alert = fixture.debugElement.query(By.css('app-alert'));
    expect(alert.properties.message).toBe(errorMsg);
    expect(compiled.querySelector('app-loading-spinner')).toBeFalsy();
    expect(compiled.querySelector('#inputForm')).toBeTruthy();
  });

  it('should not have error component if error is present but loading', () => {
    component.error = 'error msg';
    component.isLoading = true;
    fixture.detectChanges();

    let compiled = fixture.nativeElement;
    expect(compiled.querySelector('app-loading-spinner')).toBeTruthy();
    expect(compiled.querySelector('#inputForm')).toBeFalsy();
  });


});
