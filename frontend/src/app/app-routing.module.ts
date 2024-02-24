import { Routes, RouterModule } from '@angular/router';
import { NgModule } from '@angular/core';
import { provideRouter } from '@angular/router';
import { PostUserComponent } from './component/post-user/post-user.component';
import { GetUserComponent } from './component/get-user/get-user.component';
import { LoginComponent } from './login/login.component';
import { UpdateUserComponent } from './component/update-user/update-user.component';

export const routes: Routes = [
    { path: '', redirectTo: '/login', pathMatch: 'full' },
    {path:'user', component: GetUserComponent},
    {path:'create', component: PostUserComponent},
    {path:'login', component: LoginComponent},
    {path:'user/:id', component: UpdateUserComponent}



];

providers: [provideRouter(routes)]

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})
export class AppRoutingModule { }