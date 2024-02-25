// Em auth.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const BASIC_URL = ['http://ec2-34-239-109-81.compute-1.amazonaws.com:8080'];

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http: HttpClient) {}

  login(email: string, senha: string): Observable<any> {
    return this.http.post<any>(BASIC_URL +"/usuario/login", { email, senha });
  }
}
