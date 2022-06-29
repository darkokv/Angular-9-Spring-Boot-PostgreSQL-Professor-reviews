import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TopProfessorsComponent } from './top-professors.component';

describe('TopProfessorsComponent', () => {
  let component: TopProfessorsComponent;
  let fixture: ComponentFixture<TopProfessorsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TopProfessorsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TopProfessorsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
