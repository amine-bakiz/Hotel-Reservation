import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

const url = "http://localhost:8080"

@Injectable({
  providedIn: 'root'
})
export class CustomerService {

  constructor(private http: HttpClient) { }

   getRooms(pageNumber:any):Observable<any>{
      return this.http.get(url+`/api/customer/rooms/${pageNumber}`,{
        headers: this.createAuthorizationHeader(),
      })
    }

    bookRoom(bookingDto:any):Observable<any>{
        return this.http.post(url+"/api/customer/book",bookingDto,{
          headers: this.createAuthorizationHeader(),
        })
      }

    getMyBookings(pageNumber: number): Observable<any> {
     const userId = UserStorageService.getUserId();
     return this.http.get<any>(url+`/api/customer/bookings/${userId}/${pageNumber}`,{
      headers: this.createAuthorizationHeader(),
    })
   }

    createAuthorizationHeader(){
        let authHeaders: HttpHeaders = new HttpHeaders();
        return authHeaders.set(
          'Authorization',
          'Bearer ' + UserStorageService.getToken()
        )
      } 
}
