import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdminService } from '../../services/admin.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-update-room',
  templateUrl: './update-room.component.html',
  styleUrls: ['./update-room.component.scss']
})
export class UpdateRoomComponent {
  roomForm: FormGroup;
  roomId: string;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private adminService: AdminService,
    private route: ActivatedRoute
  ) {
    this.roomForm = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      price: ['', [Validators.required, Validators.min(0)]]
    });
  }
  ngOnInit(): void {
    this.roomId = this.route.snapshot.paramMap.get('id') || '';
    this.loadRoomData();
  }

  loadRoomData(): void {
    this.adminService.getRoomById(this.roomId).subscribe(
      (room) => {
        this.roomForm.patchValue({
          name: room.name,
          type: room.type,
          price: room.price
        });
      },
      (error) => {
        console.error('Error loading room data:', error);
        alert('Failed to load room data');
        this.router.navigate(['/admin/room']);
      }
    );
  }
  onSubmit(): void {
    if (this.roomForm.valid) {
      this.adminService.updateRoom(this.roomId, this.roomForm.value).subscribe(
        (res) => {
          console.log('Room updated successfully:', res);
          alert('Room updated successfully!');
          this.router.navigate(['/admin/dashbord']);
        },
        (error) => {
          console.error('Error updating room:', error);
          alert('Failed to update room');
        }
      );
    }
  }
}
