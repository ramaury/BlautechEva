import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/models/user.model';
import { Item } from 'src/app/models/item.model';

@Component({
  selector: 'app-user-details',
  templateUrl: './user-details.component.html',
})
export class UserDetailsComponent {
  @Input() viewMode = false;

  @Input() currentUser: User = {
    name: '',
    fullname: ''
  };

  message = '';

  constructor(
    private userService: UserService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.viewMode) {
      this.message = '';
      this.getUser(this.route.snapshot.params['id']);
    }
  }

  getUser(id: number): void {
    let request = new Item();
    request.id = id;
    this.userService.get(request).subscribe({
      next: (data) => {
        this.currentUser = data;
        console.log(data);
      },
      error: (e) => console.error(e)
    });
  }

  updateUser(): void {
    this.message = '';

    this.userService
      .update(this.currentUser)
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

  deleteUser(): void {
    let request = new Item();
    request.id = this.currentUser.id;
    this.userService.delete(request).subscribe({
      next: (res) => {
        console.log(res);
        this.router.navigate(['/users']);
      },
      error: (e) => console.error(e)
    });
  }
}
