import { Component } from '@angular/core';
import { Task } from 'src/app/models/task.model';
import { TaskService } from 'src/app/services/task.service';
import { UserService } from 'src/app/services/user.service';

interface StatusValue {
  id: number;
  viewValue: string;
}

@Component({
  selector: 'app-add-task',
  templateUrl: './add-task.component.html',
})
export class AddTaskComponent {
  task: Task = {
    title: '',
    description: '',
    user:0
  };

  userList: StatusValue[] = [];
  submitted = false;

  constructor(
    private taskService: TaskService,
    private userService: UserService,
  ) {}

  ngOnInit(): void {
    this.getUsersList();
  }

  saveTask(): void {
    const data = {
      title: this.task.title,
      description: this.task.description,
      user:this.task.user
    };

    this.taskService.create(data).subscribe({
      next: (res) => {
        console.log(res);
        this.submitted = true;
      },
      error: (e) => console.error(e)
    });
  }

  newTask(): void {
    this.submitted = false;
    this.task = {
      title: '',
      description: '',
      user:0
    };
  }

  getUsersList(): void {
    this.userList = [];
    this.userService.getUserList().subscribe({
      next: (data) => {
        if(data != null && data.length > 0){
          for (var val of data) {
            if(this.task.user == 0){
              this.task.user = val.id;
            }
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
