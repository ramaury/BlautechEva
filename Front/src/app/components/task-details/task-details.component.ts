import { Component, Input, OnInit } from '@angular/core';
import { TaskService } from 'src/app/services/task.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Task } from 'src/app/models/task.model';
import { Item } from 'src/app/models/item.model';
import {MatInputModule} from '@angular/material/input';
import {MatSelectModule} from '@angular/material/select';
import {MatFormFieldModule} from '@angular/material/form-field';
import {FormsModule} from '@angular/forms';
import { UserService } from 'src/app/services/user.service';

interface StatusValue {
  id: number;
  viewValue: string;
}

@Component({
  selector: 'app-task-details',
  templateUrl: './task-details.component.html',
})
export class TaskDetailsComponent {
[x: string]: any;
  @Input() viewMode = false;

  @Input() currentTask: Task = {
    title: '',
    description: '',
    status:0,
    user:0
  };

  statusList: StatusValue[] = [
    {id: 0, viewValue: 'Pendiente'},
    {id: 1, viewValue: 'Progreso'},
    {id: 2, viewValue: 'Terminado'},
  ];

  userList: StatusValue[] = [];

  message = '';
  constructor(
    private taskService: TaskService,
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getTask(this.route.snapshot.params['id']);
      this.getUsersList();
    }
  }

  getTask(id: number): void {
    let request = new Item();
    request.id = id;
    this.taskService.get(request).subscribe({
      next: (data) => {
        this.currentTask = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  updateTask(): void {
    this.message = '';

    console.log("send:" + this.currentTask.status);
    this.taskService
      .update(this.currentTask)
      .subscribe({
        next: (res) => {
          console.log(res);
          this.message = res.message
            ? res.message
            : 'Actualizado';
        },
        error: (e) => console.error(e)
      });
  }

  deleteTask(): void {
    let request = new Item();
    request.id = this.currentTask.id;
    this.taskService.delete(request).subscribe({
      next: (res) => {
        console.log(res);
        this.router.navigate(['/tasks']);
      },
      error: (e) => console.error(e)
    });
  }

  getUsersList(): void {
    this.userList = [];
    this.userService.getUserList().subscribe({
      next: (data) => {
        if(data != null && data.length > 0){
          for (var val of data) {
            this.userList.push({id: val.id, viewValue: val.name!});
          }
        } else {
          this.userList = [
            {id: 0, viewValue: 'No hay usuarios disponibles'}     
          ];
        }       
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }
}
