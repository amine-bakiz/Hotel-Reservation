import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { UserStorageService } from 'src/app/services/storage/user-storage.service';

const url = "http://localhost:8080"

@Injectable({
  providedIn: 'root'
})
export class AdminService {
 

  constructor(private http:HttpClient) { }

  postRoom(room:any):Observable<any>{
    return this.http.post(url+"/api/admin/room",room,{
      headers: this.createAuthorizationHeader(),
    });
  }

  getRooms(pageNumber:any):Observable<any>{
    return this.http.get(url+`/api/admin/rooms/${pageNumber}`,{
      headers: this.createAuthorizationHeader(),
    });
  }

  createAuthorizationHeader(){
    let authHeaders: HttpHeaders = new HttpHeaders();
    return authHeaders.set(
      'Authorization',
      'Bearer ' + UserStorageService.getToken()
    )
  } 

  getRoomById(id: string): Observable<any> {
    return this.http.get(url+`/api/admin/room/${id}`,{
      headers: this.createAuthorizationHeader(),
    });
  }
  
  updateRoom(id: string, roomData: any): Observable<any> {
    return this.http.put(url+`/api/admin/room/${id}`, roomData ,{
      headers: this.createAuthorizationHeader(),
    });
  }

  deleteRoomById(roomId: number): Observable<any> {
    return this.http.delete(url+`/api/admin/room/${roomId}`,{
      headers: this.createAuthorizationHeader(),
    });
  }

  getReservations(pageNumber:any):Observable<any>{
    return this.http.get(url+`/api/admin/reservation/${pageNumber}`,{
      headers: this.createAuthorizationHeader(),
    })
  }

  changeReservationstatus(id:number, status:String): Observable<any>{
    return this.http.get(url+`/api/admin/reservation/${id}/${status}`,{
      headers: this.createAuthorizationHeader(),
    })
  }
  
}
