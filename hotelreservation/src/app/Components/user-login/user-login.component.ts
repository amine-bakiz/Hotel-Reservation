import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/services/auth/auth.service';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

@Component({
  selector: 'app-user-login',
  templateUrl: './user-login.component.html',
  styleUrls: ['./user-login.component.scss']
})
export class UserLoginComponent {

  user = {
    email: '',
    password: '',
  };

   constructor(private router: Router, private loginService:AuthService) {}


  login() {
  this.loginService.login(this.user).subscribe(res=>{
    console.log(res);
    if(res.userId != null){
      const user  = {
        id : res.userId,
        role : res.userRole
      };
      UserStorageService.saveUser(user);
      UserStorageService.saveToken(res.jwt);
    if(UserStorageService.isAdminLoggedIn()){
      this.router.navigateByUrl('/admin/dashbord');
    }else if(UserStorageService.isCustomerLoggedIn()){
      this.router.navigateByUrl('/customers/rooms');
    }
    }
 },error=>alert("Please enter correct username and password"));
}}
