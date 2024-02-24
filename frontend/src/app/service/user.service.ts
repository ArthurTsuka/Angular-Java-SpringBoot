import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASIC_URL = ['http://ec2-52-202-212-23.compute-1.amazonaws.com:8080'];

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }

  postUser(user:any): Observable<any>{
    return this.http.post(BASIC_URL +"/usuario", user);

  }

  getAllUsers(): Observable<any> {
    return this.http.get(BASIC_URL+"/usuario");
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(BASIC_URL+"/usuario/"+id);
  }

  updateUser(id: number, usuario: any): Observable<any> {
    return this.http.put(BASIC_URL+"/usuario/"+id, usuario);
  }

  deleteUser(id: number): Observable<string> {
    return this.http.delete(BASIC_URL+"/usuario/"+id , { responseType: 'text' });
  }
  

}
