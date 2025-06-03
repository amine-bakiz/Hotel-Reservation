import { Injectable } from '@angular/core';

const TOKEN = 'token';
const User = 'user';

@Injectable({
  providedIn: 'root'
})
export class UserStorageService {

  constructor() { }


  static saveToken(token:string):void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.setItem(TOKEN,token);
  }

  static saveUser(user:any):void{
    window.localStorage.removeItem(User);
    window.localStorage.setItem(User,JSON.stringify(user));
  }

  static getToken():String{
    return localStorage.getItem(TOKEN);
  }

  static getUser():any{
    return JSON.parse(localStorage.getItem(User));
  }

  static getUserId():string{
    const user=this.getUser();
    if(user==null){return '';}
    return user.id;
  }

  static getUserRole():string{
    const user=this.getUser();
    if(user==null){return '';}
    return user.role;
  }

  static isAdminLoggedIn():boolean{
    if(this.getToken == null){
      return false;
    }
    const role:string=this.getUserRole();
    return role == 'ADMIN';
  }

  static isCustomerLoggedIn():boolean{
    if(this.getToken == null){
      return false;
    }
    const role:string=this.getUserRole();
    return role == 'CUSTOMER';
  }

  static signOut():void{
    window.localStorage.removeItem(TOKEN);
    window.localStorage.removeItem(User);
  }
  
}