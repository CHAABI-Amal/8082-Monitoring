import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ManageTypeMachineComponent } from './manage-type-machine.component';

describe('ManageTypeMachineComponent', () => {
  let component: ManageTypeMachineComponent;
  let fixture: ComponentFixture<ManageTypeMachineComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ManageTypeMachineComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ManageTypeMachineComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
