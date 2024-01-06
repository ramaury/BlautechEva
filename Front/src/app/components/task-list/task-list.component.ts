import { Component, OnInit } from '@angular/core';
import { Item } from 'src/app/models/item.model';
import { Task } from 'src/app/models/task.model';
import { TaskService } from 'src/app/services/task.service';
import { UserService } from 'src/app/services/user.service';

interface StatusValue {
  id: number;
  viewValue: string;
}

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
})

export class TasksListComponent {
  tasks?: Task[];
  currentTask: Task = {};
  currentIndex = -1;
  userList: StatusValue[] = [];
user: any;
  constructor(
    private taskService: TaskService,
    private userService: UserService,) {}

  ngOnInit(): void {
    this.getTasksList(0);
    this.getUsersList();
  }

  filterList(event: any){
    this.getTasksList(event.target.value);
  }

  getTasksList(idUser: number): void {
    let request = new Item();
    request.id = idUser;
    this.taskService.getTaskList(request).subscribe({
      next: (data) => {
        this.tasks = data;
        if(data != null){         
          for (var val of this.tasks) {
            switch(val.status){
              case(0) : {
                val.statusText = 'Pendiente';
                break;     
              }
              case(1) : {
                val.statusText = 'Progreso';
                break;
              }
              case(2) : {
                val.statusText = 'Terminado';
                break;
              }
              default : {             
                break;
              }
            }
          }
        }
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  setActiveTask(task: Task, index: number): void {
    this.currentTask = task;
    this.currentIndex = index;
  }

  getUsersList(): void {
    this.userList = [];
    this.userService.getUserList().subscribe({
      next: (data) => {
        if(data != null && data.length > 0){
          this.userList.push({id: 0, viewValue: "Todos"});
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
