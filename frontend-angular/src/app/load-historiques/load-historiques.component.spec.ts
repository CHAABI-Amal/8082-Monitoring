import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadHistoriquesComponent } from './load-historiques.component';

describe('LoadHistoriquesComponent', () => {
  let component: LoadHistoriquesComponent;
  let fixture: ComponentFixture<LoadHistoriquesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadHistoriquesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoadHistoriquesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
