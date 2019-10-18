import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EmpAbsencesIndexComponent } from './emp-absences-index.component';

describe('EmpAbsencesIndexComponent', () => {
  let component: EmpAbsencesIndexComponent;
  let fixture: ComponentFixture<EmpAbsencesIndexComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EmpAbsencesIndexComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EmpAbsencesIndexComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
