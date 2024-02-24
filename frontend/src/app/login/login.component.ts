import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../service/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})

export class LoginComponent {
  model: any = {};

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit(formValue: any) {
    this.authService.login(formValue.email, formValue.password).subscribe({
      next: (response) => {
        console.log('Sucesso no login');
        localStorage.setItem('user', JSON.stringify(response));
        this.router.navigateByUrl("/user")
      },
      error: (error) => {
        console.error('Erro no login');
      }
    });
  }

  navigateToCreateAccount() {
    this.router.navigateByUrl('/create');
  }

}
