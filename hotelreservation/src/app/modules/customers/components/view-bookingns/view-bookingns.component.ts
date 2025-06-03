import { Component, OnInit } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

@Component({
  selector: 'app-view-bookingns',
  templateUrl: './view-bookingns.component.html',
  styleUrls: ['./view-bookingns.component.scss']
})
export class ViewBookingnsComponent implements OnInit {

  isLoading: boolean = true;
  currentPage: number = 1;
  bookings: any[] = [];
  total: number = 0;
  errorMessage: string = '';

  constructor(private customerService: CustomerService) {}

  ngOnInit() {
    console.log('Current Token:', UserStorageService.getToken());
    console.log('Current User ID:', UserStorageService.getUserId());
    this.loadBookings();
  }

  loadBookings() {
    this.isLoading = true;
    const userId = UserStorageService.getUserId();
    
    this.customerService.getMyBookings( this.currentPage - 1).subscribe({
      next: (res: any) => {
         console.log('API Response:', res);
        this.bookings = res.reservationDtoList || [];
        this.total = res.totalPages || 1;
        this.isLoading = false;
      },
      error: (err) => {
        console.error('Failed to load bookings:', err);
        this.errorMessage = 'Could not load reservations.';
        this.isLoading = false;
      }
    });
  }

  pageIndexChange(newPage: number) {
    this.currentPage = newPage;
    this.loadBookings();
  }
}
