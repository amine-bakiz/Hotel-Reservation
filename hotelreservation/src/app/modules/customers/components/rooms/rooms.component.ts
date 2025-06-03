import { Component } from '@angular/core';
import { CustomerService } from '../../services/customer.service';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

interface Room {
  id: number;
  name: string;
  type: string;
  price: number;
  currentlyAvailable: boolean;
  nextAvailableDate?: Date;
  availableFrom?: Date;
}

@Component({
  selector: 'app-rooms',
  templateUrl: './rooms.component.html',
  styleUrls: ['./rooms.component.scss']
})
export class RoomsComponent {
  bookingForm: FormGroup;
  currentPage = 1;
  rooms: Room[] = [];
  total = 0;
  isVisibleMiddle = false;
  selectedRoom?: Room;
  minDate = new Date();
  roomAvailabilityMessage = '';

  constructor(
    private customerService: CustomerService,
    private fb: FormBuilder,
  ) {
    this.bookingForm = this.fb.group({
      checkInDate: ['', Validators.required],
      checkOutDate: ['', Validators.required]
    });
    this.getRooms();
  }

  isRoomAvailable(room: Room): boolean {
    if (!room.nextAvailableDate) return true;
    const today = new Date();
    today.setHours(0, 0, 0, 0);
    const availableDate = new Date(room.nextAvailableDate);
    return availableDate <= today;
  }

  getRooms() {
    this.customerService.getRooms(this.currentPage - 1).subscribe({
      next: (res: any) => {
        this.rooms = (res.roonDtoList ?? []).map((room: any) => ({
          ...room,
          nextAvailableDate: room.nextAvailableDate ? new Date(room.nextAvailableDate) : null
        }));
        this.total = res.totalPage ?? 1;
      },
      error: (err) => console.error(err)
    });
  }

  changePage(page: number | string) {
    if (typeof page === 'number') {
      if (page >= 1 && page <= this.total) {
        this.currentPage = page;
        this.getRooms();
      }
    }
  }

  getPages(): (number | string)[] {
    const pages = [];
    const maxVisiblePages = 5;
    if (this.total > maxVisiblePages) {
      pages.push(1, 2, '...', this.total - 1, this.total);
    } else {
      for (let i = 1; i <= this.total; i++) pages.push(i);
    }
    return pages;
  }

  handleCancelMiddle() {
    this.isVisibleMiddle = false;
    this.bookingForm.reset();
    this.roomAvailabilityMessage = '';
  }

  handleOkMiddle(): void {
    if (this.bookingForm.valid && this.selectedRoom) {
      const formValue = this.bookingForm.value;
      const checkIn = new Date(formValue.checkInDate);
      const checkOut = new Date(formValue.checkOutDate);
      const nextAvailable = this.selectedRoom.nextAvailableDate;

      if (checkOut <= checkIn) {
        alert('Check-out date must be after check-in date');
        return;
      }

      if (nextAvailable && checkIn < nextAvailable) {
        this.roomAvailabilityMessage = `⚠️ This room is occupied until ${nextAvailable.toLocaleDateString()}`;
        return;
      }

      const obj = {
        userId: UserStorageService.getUserId(),
        roomId: this.selectedRoom.id,
        checkIn: formValue.checkInDate,
        checkOut: formValue.checkOutDate
      };

      this.customerService.bookRoom(obj).subscribe({
        next: () => {
          alert('Booking successful!');
          this.isVisibleMiddle = false;
          this.bookingForm.reset();
          this.roomAvailabilityMessage = '';
          this.getRooms();
        },
        error: (err) => {
          const errorMsg = this.getErrorMessage(err);
          alert(`Booking failed: ${errorMsg}`);
          console.error('Booking error:', err);
        }
      });
    }
  }

  showModalMiddle(id: number) {
    this.selectedRoom = this.rooms.find(room => room.id === id);
    if (!UserStorageService.isCustomerLoggedIn()) {
      alert('Please login to book rooms');
      return;
    }
    if (this.selectedRoom?.nextAvailableDate && new Date(this.selectedRoom.nextAvailableDate) > new Date()) {
      this.roomAvailabilityMessage = `⚠️ This room is occupied until ${new Date(this.selectedRoom.nextAvailableDate).toLocaleDateString()}. You can still book after that.`;
    } else {
      this.roomAvailabilityMessage = '';
    }
    this.isVisibleMiddle = true;
  }

  private getErrorMessage(error: any): string {
    if (error.status === 403) {
      return 'Access forbidden - please check your authentication';
    }
    return error.error?.message || error.message || 'Unknown error occurred';
  }
}
