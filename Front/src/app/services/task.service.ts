import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from '../models/task.model';

const baseUrl = 'http://localhost:8080/api';

@Injectable({
  providedIn: 'root',
})
export class TaskService {
  constructor(private http: HttpClient) {}

  getTaskList(data: any): Observable<Task[]> {
    return this.http.post<Task[]>(`${baseUrl}/getTaskList`, data);
  }

  get(data: any): Observable<Task> {
    return this.http.post<Task>(`${baseUrl}/getTaskById`, data);
  }

  create(data: any): Observable<any> {
    return this.http.put(`${baseUrl}/saveTask`, data);
  }

  update(data: any): Observable<any> {
    return this.http.post(`${baseUrl}/editTask`, data);
  }

  delete(data: any): Observable<any> {
    return this.http.post(`${baseUrl}/deleteTask`, data);
  }

}
