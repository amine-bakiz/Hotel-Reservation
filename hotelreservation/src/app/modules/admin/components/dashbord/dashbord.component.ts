import { Component } from '@angular/core';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-dashbord',
  templateUrl: './dashbord.component.html',
  styleUrls: ['./dashbord.component.scss']
})
export class DashbordComponent {

  currentPage = 1;
  rooms = [];
  total :any;

  constructor(private adminService : AdminService){
    this.getRooms();
  }

  getRooms(){
    this.adminService.getRooms(this.currentPage -1).subscribe(res=>{
      console.log(res);
      this.rooms = res.roonDtoList ?? [];
    
  
      this.total = res.totalPages ?? 1;
    })
  }

  changePage(page: number): void {
    if (page >= 1 && page <= this.total) {
      this.currentPage = page;
      this.getRooms();
    }
  }

  getPages(): number[] {
    const pages = [];
    const maxVisiblePages = 5; // Adjust how many page numbers to show
    
    let startPage = Math.max(1, this.currentPage - Math.floor(maxVisiblePages / 2));
    let endPage = startPage + maxVisiblePages - 1;
    
    if (endPage > this.total) {
      endPage = this.total;
      startPage = Math.max(1, endPage - maxVisiblePages + 1);
    }
    for (let i = startPage; i <= endPage; i++) {
      pages.push(i);
    }
    
    return pages;
  }

  deleteRoom(roomId: number) {
    if (confirm('Are you sure you want to delete this room?')) {
      this.adminService.deleteRoomById(roomId).subscribe(
        (res) => {
          alert('Deleted successfully!');
          // Refresh the room list
          this.rooms = this.rooms.filter(room => room.id !== roomId);
          // Alternative: Reload full list from server
          // this.loadRooms(this.currentPage);
        },
        (error) => {
          console.error('Error deleting room:', error);
          alert('Failed to delete room');
        }
      );
    }
  }

}
