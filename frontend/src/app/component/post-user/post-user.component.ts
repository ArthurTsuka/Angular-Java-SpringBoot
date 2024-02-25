import { Component, OnInit } from '@angular/core';
import { UserService } from '../../service/user.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-post-user',
  templateUrl: './post-user.component.html',
  styleUrls: ['./post-user.component.css']
})
export class PostUserComponent implements OnInit {

  postUserForm: FormGroup = new FormGroup({});

  constructor(
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router
  ) {}

  ngOnInit() {
    this.initializeForm();
  }

  initializeForm(): void {
    this.postUserForm = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email, Validators.pattern]],
      cpf: ['', [Validators.required, Validators.pattern]],
      senha: ['', Validators.required],
      nacionalidade: ['', Validators.required],
      telefone: ['', [Validators.required, Validators.pattern]],
      dataNascimento: ['', Validators.required],
    });
  }

  postUser() {
    if (this.postUserForm.valid) {
      this.userService.postUser(this.postUserForm.value).subscribe({
        next: (res) => {
          console.log('Usuário criado com sucesso:', res);
          this.router.navigateByUrl("/user");
        },
        error: (error) => {
          console.error('Erro ao criar usuário:', error);
        }
      });
    } else {
      console.error('Formulário inválido');
    }
  }
}
