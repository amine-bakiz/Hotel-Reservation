import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-reservations',
  templateUrl: './reservations.component.html',
  styleUrls: ['./reservations.component.scss']
})
export class ReservationsComponent {

  currentPage :any =1;
  total:any;
  reservation:any [];

  constructor(private adminService :AdminService){
    this.getreservations();
  }

  getreservations(){
    this.adminService.getReservations(this.currentPage-1).subscribe(res=>{
      console.log(res);
      this.reservation = res.reservationDtoList;
      this.total = res.totalPages * 5;
    })
  }

  pageIndexChange(value:any){
    this.currentPage = value;
    this.getreservations();
  }


  changeReservationStatus(id: number, newStatus: string) {
  this.adminService.changeReservationstatus(id, newStatus).subscribe({
    next: () => {
      // Update local state
      this.reservation = this.reservation.map(res => {
        if (res.id === id) {
          return { 
            ...res, 
            reservationstatus: newStatus.toUpperCase(),
            room: {
              ...res.room,
              available: newStatus.toLowerCase() === 'rejected'
            }
          };
        }
        return res;
      });
    },
    error: (err) => {
      console.error('Status update failed:', err);
      // Add user notification here
    }
  });
}

}
