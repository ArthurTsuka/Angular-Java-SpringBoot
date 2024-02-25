import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../../service/user.service';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrls: ['./update-user.component.css']
})
export class UpdateUserComponent implements OnInit {

  id: number;
  updateUserForm: FormGroup;

  constructor(
    private activatedRoute: ActivatedRoute,
    private userService: UserService,
    private fb: FormBuilder,
    private router: Router,
  ) {
    this.id = this.activatedRoute.snapshot.params['id'];
    this.updateUserForm = this.fb.group({});
  }

  ngOnInit() {
    this.initializeForm();
    this.getUserById();
  }

  initializeForm(): void {
    this.updateUserForm = this.fb.group({
      nome: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      cpf: ['', [Validators.required]],
      senha: ['', Validators.required],
      nacionalidade: ['', Validators.required],
      telefone: ['', [Validators.required]],
      dataNascimento: ['', Validators.required],
    });
  }

  getUserById() {
    if (this.id) {
      this.userService.getUserById(this.id).subscribe((res) => {
        console.log(res);
        const formattedRes = {
          ...res,
          dataNascimento: res.dataNascimento ? new Date(res.dataNascimento).toISOString().substring(0, 10) : null
        };
        this.updateUserForm.patchValue(formattedRes);
      }, error => {
        console.error("Erro ao buscar usuário:", error);
      });
    }
  }

  updateUser() {
    if (this.updateUserForm.valid) {
      this.userService.updateUser(this.id, this.updateUserForm.value).subscribe({
        next: (res) => {
          console.log('Usuário atualizado com sucesso:', res);
          this.router.navigateByUrl("/user");
        },
        error: (error) => {
          console.error('Erro ao atualizar usuário:', error);
        }
      });
    } else {
      console.error('Formulário de atualização inválido');
    }
  }
}
