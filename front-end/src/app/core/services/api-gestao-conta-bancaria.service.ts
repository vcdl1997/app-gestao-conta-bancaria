import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';
import Swal from 'sweetalert2';
import { CadastroConta } from '../models/cadastro-conta.model';
import { CadastroTransferencia } from '../models/cadastro-transferencia.model';

@Injectable({
  providedIn: 'root',
})
export class ApiGestaoContaBancariaService {

	private baseUrl = 'http://localhost:8080/gestao-conta-bancaria/v1'; 
	private resourceUrl = 'contas'; 

	constructor(private http: HttpClient) { }

	listarContas(queryParams: string): Observable<any> {
		return this.http
			.get(`${this.baseUrl}/${this.resourceUrl}${queryParams}`)
			.pipe(catchError(this.errorHandler)); 
		;
	}

	obterContaPorId(idConta: number): Observable<any> {
		return this.http
			.get(`${this.baseUrl}/${this.resourceUrl}/${idConta}`)
			.pipe(catchError(this.errorHandler)); 
		;
	}

	cadastrarConta(body: CadastroConta): Observable<any> {
		return this.http
			.post(`${this.baseUrl}/${this.resourceUrl}`, body)
			.pipe(catchError(this.errorHandler)); 
		;
	}

	transferenciaEntreContas(idConta: number, body: CadastroTransferencia): Observable<any> {
		return this.http
			.post(`${this.baseUrl}/${this.resourceUrl}/${idConta}/transferencias`, body)
			.pipe(catchError(this.errorHandler)); 
		;
	}

	errorHandler(response: HttpErrorResponse) {
		const { error, status } = response;
		const messageDefault = "Algo ruim aconteceu; por favor tente novamente mais tarde.";

		switch(status){
			case 400: case 422: 
				let lis: string = Array.isArray(error) ? 
					error.map((err: any) => `<li>O campo “${err.campo}” ${err?.mensagem.toLowerCase()}</li>`).join('') :
					`<li>${error.campo ? `O campo “${error.campo}” ${error?.mensagem.toLowerCase()}` : error?.mensagem}</li>`;
			
				Swal.fire({ icon: "info", title: "Oops...", html: `<ul id="form-list-errors">${lis}</ul>`, timer: 10000 });
			break;

			default:
				Swal.fire({ icon: "error", title: "Erro", text: messageDefault, timer: 400000000 });
			break;
		}

		return throwError("Foram encontrados erros ao executar essa funcionalidade; por favor tente novamente mais tarde.");
	}
}
