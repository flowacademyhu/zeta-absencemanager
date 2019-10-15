import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AbsencesCreateComponent } from './absences-create.component';

describe('AbsencesCreateComponent', () => {
  let component: AbsencesCreateComponent;
  let fixture: ComponentFixture<AbsencesCreateComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AbsencesCreateComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AbsencesCreateComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
