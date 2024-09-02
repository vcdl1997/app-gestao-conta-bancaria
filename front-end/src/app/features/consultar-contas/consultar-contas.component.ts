import { Component } from '@angular/core';
import { SharedComponentsModule } from "../../shared/components/shared-components.module";
import { BreadcrumbItem } from '../../core/models/breadcrumb-item.model';
import { Router } from '@angular/router';
import { ApiGestaoContaBancariaService } from '../../core/services/api-gestao-conta-bancaria.service';
import { BREADCRUMB_TELA_CONSULTA, CAMPOS_JSON_CONSULTA, COLUNAS_TELA_CONSULTA } from '../../core/constants/global.constants';
import { RowItem } from '../../core/models/row-item.model';
import { RowColumn } from '../../core/models/row-column.model';
import { FormBuilder, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { FiltroConta } from '../../core/models/filtro-conta.model';
import { FormUtils } from '../../core/utils/FormUtils';

@Component({
  selector: 'consultar-contas',
  standalone: true,
  imports: [SharedComponentsModule],
  templateUrl: './consultar-contas.component.html',
  styleUrl: './consultar-contas.component.css'
})
export class ConsultarContasComponent {

	breadcrumb: BreadcrumbItem[] = BREADCRUMB_TELA_CONSULTA;
	columns: RowColumn[] = COLUNAS_TELA_CONSULTA;
	rowColumns: RowItem[] = CAMPOS_JSON_CONSULTA;
	data: any[] = [];
	totalRecords: number = 0;
	rowsPerPage: number = 10; 
	currentPage: number = 0;
	filtros: any;

	constructor(
		private router: Router, 
		private formBuilder: FormBuilder,
		private api: ApiGestaoContaBancariaService
	) {}

	ngOnInit() {
		this.loadData(this.currentPage, this.rowsPerPage);
		this.limpar();
	}

	buscar(){
		this.loadData(this.currentPage, this.rowsPerPage);
	}

	limpar(){
		this.filtros = this.formBuilder.group({
			idConta: [''],
			codigoAgencia: [''],
			numeroConta: [''],
			nomeTitular: [''],
			documentoIdentificacaoTitular: ['']
		});
	}

	onPage(event: any): void {
		this.currentPage = event.first / event.rows;
    	this.rowsPerPage = event.rows;
		this.loadData(this.currentPage, this.rowsPerPage);
	}
	
	loadData(page: number, size: number): void {
		const params = this.getFilters(page, size);
		this.api.listarContas(params).subscribe((response: any) => {
			this.data = response.content;
			this.totalRecords = response.totalElements;
		});
	}

	private getFilters(page: number, size: number): string{
		let filtroConta: FiltroConta = this.filtros?.value ?? {};
		filtroConta.page = page;
		filtroConta.size = size;

		return FormUtils.serialize(filtroConta);
	}

	inputNumber(event: any) {
		FormUtils.inputNumber(event);
	}
}
