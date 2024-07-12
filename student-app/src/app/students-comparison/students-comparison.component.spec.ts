import { ComponentFixture, TestBed } from '@angular/core/testing';

import { StudentsComparisonComponent } from './students-comparison.component';

describe('StudentsComparisonComponent', () => {
  let component: StudentsComparisonComponent;
  let fixture: ComponentFixture<StudentsComparisonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [StudentsComparisonComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(StudentsComparisonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
