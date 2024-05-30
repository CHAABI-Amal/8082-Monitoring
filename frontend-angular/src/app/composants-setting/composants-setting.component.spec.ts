import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ComposantsSettingComponent } from './composants-setting.component';

describe('ComposantsSettingComponent', () => {
  let component: ComposantsSettingComponent;
  let fixture: ComponentFixture<ComposantsSettingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ComposantsSettingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ComposantsSettingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
