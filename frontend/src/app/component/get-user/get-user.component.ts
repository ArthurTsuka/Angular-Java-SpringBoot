import { Component } from '@angular/core';
import { UserService } from '../../service/user.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-get-user',
  templateUrl: './get-user.component.html',
  styleUrl: './get-user.component.css'
})
export class GetUserComponent {

  users: any[] = []; 

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit() {
    this.getAllUsers();
    
  }

  getAllUsers() {
    this.userService.getAllUsers().subscribe((res) => {
      this.users = res;
    })
  }

  deleteUser(id: number) {
    this.userService.deleteUser(id).subscribe((res) => {
      console.log(res);
      this.getAllUsers();
    })
  }

}
