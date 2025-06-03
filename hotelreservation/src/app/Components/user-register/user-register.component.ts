import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';

@Component({
  selector: 'app-user-register',
  templateUrl: './user-register.component.html',
  styleUrls: ['./user-register.component.scss']
})
export class UserRegisterComponent {

  
    user = {
      username:'',
      email: '',
      password: '',
    };


  constructor(private router: Router, private registerService:AuthService) {}

  register() {
    this.registerService.register(this.user).subscribe(data=>{
      alert("signup Successfully")
      this.router.navigate(['/']);
    },error=>alert("Please enter correct username and password"));
    }

  
  
}
