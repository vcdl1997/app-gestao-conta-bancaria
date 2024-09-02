import { Transacao } from "./transacao.model";

export interface Conta {
	id: number;
	codigoAgencia: number;
	numero: number;
	nomeTitular: string;
	tipoTitular: string;
	documentoIdentificacaoTitular: string;
	dataAberturaConta: string,
	saldo: number;
	extrato: Transacao[];
}