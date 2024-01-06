import { Component } from '@angular/core';
import { User } from 'src/app/models/user.model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-add-user',
  templateUrl: './add-user.component.html',
})
export class AddUserComponent {
  user: User = {
    name: '',
    fullname: ''
  };
  submitted = false;

  constructor(private userService: UserService) {}

  saveUser(): void {
    const data = {
      name: this.user.name,
      fullname: this.user.fullname
    };

    this.userService.create(data).subscribe({
      next: (res) => {
        console.log(res);
        this.submitted = true;
      },
      error: (e) => console.error(e)
    });
  }

  newUser(): void {
    this.submitted = false;
    this.user = {
      name: '',
      fullname: ''
    };
  }
}
