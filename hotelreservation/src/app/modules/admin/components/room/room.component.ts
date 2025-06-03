import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AdminService } from '../../services/admin.service';

@Component({
  selector: 'app-room',
  templateUrl: './room.component.html',
  styleUrls: ['./room.component.scss']
})
export class RoomComponent {

  roomform: FormGroup;
  selectedFile: File | null = null;

  constructor(
    private fb: FormBuilder,
    private router: Router,
    private adminService: AdminService
  ) {
    this.roomform = this.fb.group({
      name: ['', Validators.required],
      type: ['', Validators.required],
      price: ['',Validators.required], // Added min price validation
    });
  }

  onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    } else {
      this.selectedFile = null;
    }
  }

  onSubmit(): void {
  if (this.roomform.invalid) {
    this.roomform.markAllAsTouched();
    return;
  }
  
  // Send form values directly (no FormData)
  this.adminService.postRoom(this.roomform.value).subscribe(
    (res) => {
      console.log('Room added successfully:', res);
      alert('Room added successfully!');
      this.resetForm();
    },
    (error) => {
      console.error('Error adding room:', error);
      alert('Failed to add room. Please try again.');
    }
  );
}

  private resetForm(): void {
    this.roomform.reset();
    this.selectedFile = null;
    // Reset file input
    const fileInput = document.getElementById('photo') as HTMLInputElement;
    if (fileInput) fileInput.value = '';
  }
}