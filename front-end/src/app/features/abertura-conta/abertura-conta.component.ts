import { Component } from '@angular/core';
import { SharedComponentsModule } from '../../shared/components/shared-components.module';
import { BreadcrumbItem } from '../../core/models/breadcrumb-item.model';
import { BREADCRUMB_TELA_ABERTURA } from '../../core/constants/global.constants';
import { Router } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { ApiGestaoContaBancariaService } from '../../core/services/api-gestao-conta-bancaria.service';
import { FormUtils } from '../../core/utils/FormUtils';
import { CadastroConta } from '../../core/models/cadastro-conta.model';
import { Conta } from '../../core/models/conta.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-abertura-conta',
  standalone: true,
  imports: [SharedComponentsModule],
  templateUrl: './abertura-conta.component.html',
  styleUrl: './abertura-conta.component.css'
})
export class AberturaContaComponent {

    breadcrumb: BreadcrumbItem[] = BREADCRUMB_TELA_ABERTURA;
    form: any;

    constructor(
		private router: Router, 
		private formBuilder: FormBuilder,
		private api: ApiGestaoContaBancariaService
	) {}

    ngOnInit() {
		this.limpar();
	}

    salvar(){
        let body: CadastroConta = this.form?.value ?? {};
        
        this.api.cadastrarConta(body).subscribe((response: Conta) => {
			Swal.fire({ title: "Abertura realizada", icon: "success", timer: 1500 });
			setTimeout(() => this.router.navigate([`/contas/${response.id}`]), 1500);
		});
	}
    
	limpar(){
		this.form = this.formBuilder.group({
			codigoAgencia: ['', Validators.required],
			numeroConta: ['', Validators.required],
			nomeTitular: ['', Validators.required],
			documentoIdentificacaoTitular: ['', Validators.required],
            valorDeposito: ['', Validators.required]
		});
	}

    inputNumber(event: any) {
		FormUtils.inputNumber(event);
	}

    formatMonetary(event: any, name: string) {
        this.form.controls[name].setValue(FormUtils.monetary(event));
	}
}
