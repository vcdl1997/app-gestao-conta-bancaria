import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { SharedComponentsModule } from '../../shared/components/shared-components.module';
import { BreadcrumbItem } from '../../core/models/breadcrumb-item.model';
import { FormBuilder, Validators } from '@angular/forms';
import { ApiGestaoContaBancariaService } from '../../core/services/api-gestao-conta-bancaria.service';
import { BREADCRUMB_TELA_DETALHES_CONTA } from '../../core/constants/global.constants';
import { Conta } from '../../core/models/conta.model';
import { FormUtils } from '../../core/utils/FormUtils';
import { CadastroTransferencia } from '../../core/models/cadastro-transferencia.model';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-acessar-conta',
  standalone: true,
  imports: [SharedComponentsModule],
  templateUrl: './acessar-conta.component.html',
  styleUrl: './acessar-conta.component.css'
})
export class AcessarContaComponent {

	contaId: any;
	breadcrumb: BreadcrumbItem[] = BREADCRUMB_TELA_DETALHES_CONTA;
    conta: Conta = <Conta>{};
	visibleModalDepositar: Conta = <Conta>{};
	visibilityModalTransferir: boolean = false;
	formTransferencia: any;
	dataATransferir: any;

	constructor(
		private router: Router, 
		private formBuilder: FormBuilder,
		private api: ApiGestaoContaBancariaService,
		private activatedRoute : ActivatedRoute
	) { }

	ngOnInit(): void {
		this.contaId = this.activatedRoute.snapshot.paramMap.get("id");
		this.carregarDetalhesConta();
		this.resetFormTransferencia();
	}

	carregarDetalhesConta(){
		this.api.obterContaPorId(this.contaId).subscribe((response: Conta) => {
			this.conta = response;
		});
	}

	formatarValorMonetario(valor: number){
		return new Number(valor).toLocaleString('pt-br',{style: 'currency', currency: 'BRL'});
	}

	resetFormTransferencia(){
		this.formTransferencia = this.formBuilder.group({
			codigoAgenciaDestino: ['', Validators.required],
			numeroContaDestino: ['', Validators.required],
			valorATransferir: ['', Validators.required],
			dataATransferir: ['', Validators.required],
		});
	}

	transferir(){
		let body: CadastroTransferencia = this.formTransferencia?.value ?? {};
        
        this.api.transferenciaEntreContas(this.contaId, body).subscribe((response: Conta) => {
			this.visibilityModalTransferir = false;
			Swal.fire({ title: "Transferência realizada", icon: "success", timer: 3000 });
			this.carregarDetalhesConta();
		});
	}
	
	inputNumber(event: any) {
		FormUtils.inputNumber(event);
	}

    formatMonetary(event: any, name: string) {
        this.formTransferencia.controls[name].setValue(FormUtils.monetary(event));
	}
}
