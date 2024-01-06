import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { User } from '../models/user.model';

const baseUrl = 'http://localhost:8080/api';

@Injectable({
  providedIn: 'root',
})
export class UserService {
  constructor(private http: HttpClient) {}

  getUserList(): Observable<User[]> {
    return this.http.get<User[]>(`${baseUrl}/getUserList`);
  }

  get(data: any): Observable<User> {
    return this.http.post<User>(`${baseUrl}/getUserById`, data);
  }

  create(data: any): Observable<any> {
    return this.http.put(`${baseUrl}/saveUser`, data);
  }

  update(data: any): Observable<any> {
    return this.http.post(`${baseUrl}/editUser`, data);
  }

  delete(data: any): Observable<any> {
    return this.http.post(`${baseUrl}/deleteUser`, data);
  }

}
