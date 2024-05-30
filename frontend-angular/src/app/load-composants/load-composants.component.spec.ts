import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LoadComposantsComponent } from './load-composants.component';

describe('LoadComposantsComponent', () => {
  let component: LoadComposantsComponent;
  let fixture: ComponentFixture<LoadComposantsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [LoadComposantsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(LoadComposantsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
