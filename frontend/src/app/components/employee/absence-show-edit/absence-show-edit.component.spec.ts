import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsenceShowEditComponent } from './absence-show-edit.component';

describe('AbsenceShowEditComponent', () => {
  let component: AbsenceShowEditComponent;
  let fixture: ComponentFixture<AbsenceShowEditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AbsenceShowEditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AbsenceShowEditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
