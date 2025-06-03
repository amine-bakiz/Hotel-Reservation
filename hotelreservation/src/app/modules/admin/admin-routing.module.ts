import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './admin.component';
import { DashbordComponent } from './components/dashbord/dashbord.component';
import { RoomComponent } from './components/room/room.component';
import { UpdateRoomComponent } from './components/update-room/update-room.component';
import { ReservationsComponent } from './components/reservations/reservations.component';

const routes: Routes = [
  { path: 'dashbord', component: DashbordComponent },
  { path: 'room', component: RoomComponent },
  { path: 'room/:id/edit', component: UpdateRoomComponent },
  { path: 'room/:id/edit', component: UpdateRoomComponent },
  { path: 'reservations', component: ReservationsComponent },



];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
