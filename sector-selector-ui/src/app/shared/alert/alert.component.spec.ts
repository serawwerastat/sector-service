import {ComponentFixture, TestBed} from '@angular/core/testing';

import {AlertComponent} from './alert.component';

describe('AlertComponent', () => {
  let component: AlertComponent;
  let fixture: ComponentFixture<AlertComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AlertComponent]
    })
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AlertComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should have message', () => {
    let errorMsg = 'error msg';
    component.message = errorMsg;
    fixture.detectChanges();
    let compiled = fixture.nativeElement;
    expect(compiled.querySelector('p').textContent).toBe(errorMsg);
  });

  it('should emit on close click', () => {

    spyOn(component.close, 'emit');
    let compiled = fixture.nativeElement;
    let button = compiled.querySelector('button');
    button.dispatchEvent(new Event('click'));

    fixture.detectChanges();
    expect(component.close.emit).toHaveBeenCalled();
  });


});
