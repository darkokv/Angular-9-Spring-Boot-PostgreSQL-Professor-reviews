import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InstituteCardComponent } from './institute-card.component';

describe('InstituteCardComponent', () => {
  let component: InstituteCardComponent;
  let fixture: ComponentFixture<InstituteCardComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InstituteCardComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InstituteCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
