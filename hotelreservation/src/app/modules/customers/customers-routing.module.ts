import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CustomersComponent } from './customers.component';
import { RoomsComponent } from './components/rooms/rooms.component';
import { ViewBookingnsComponent } from './components/view-bookingns/view-bookingns.component';

const routes: Routes = [
  { path: '', component: CustomersComponent },
  { path: 'rooms', component: RoomsComponent },
    { path: 'bookigns', component: ViewBookingnsComponent }

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class CustomersRoutingModule { }
