import { Routes } from '@angular/router';
import { ConsultarContasComponent } from './features/consultar-contas/consultar-contas.component';
import { AcessarContaComponent } from './features/acessar-conta/acessar-conta.component';
import { AberturaContaComponent } from './features/abertura-conta/abertura-conta.component';

export const routes: Routes = [
    {
        path: '',
        component: ConsultarContasComponent
    },
    {
        path: 'abertura',
        component: AberturaContaComponent
    },
    {
        path: 'contas/:id',
        component: AcessarContaComponent
    }
];
