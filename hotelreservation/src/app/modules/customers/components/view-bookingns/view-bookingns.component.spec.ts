import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBookingnsComponent } from './view-bookingns.component';

describe('ViewBookingnsComponent', () => {
  let component: ViewBookingnsComponent;
  let fixture: ComponentFixture<ViewBookingnsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ViewBookingnsComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ViewBookingnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
