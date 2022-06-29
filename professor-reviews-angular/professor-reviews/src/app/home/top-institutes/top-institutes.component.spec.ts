import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopInstitutesComponent } from './top-institutes.component';

describe('TopInstitutesComponent', () => {
  let component: TopInstitutesComponent;
  let fixture: ComponentFixture<TopInstitutesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopInstitutesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopInstitutesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
