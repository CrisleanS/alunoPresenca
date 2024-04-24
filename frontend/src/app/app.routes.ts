import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { CadOficinaComponent } from './cad-oficina/cad-oficina.component';
import { CadProfessorComponent } from './cad-professor/cad-professor.component';

export const routes: Routes = [
    { path: '',redirectTo: '/login', pathMatch: 'full' },
    { path: 'login', component: LoginComponent },
    { path: 'home', component: HomeComponent },
    { path: 'cad-oficina', component: CadOficinaComponent },
    { path: 'cad-professor', component: CadProfessorComponent }
];

@NgModule({
    imports: [RouterModule.forRoot(routes)],
    exports: [RouterModule]
})

export class AppRoutes { }