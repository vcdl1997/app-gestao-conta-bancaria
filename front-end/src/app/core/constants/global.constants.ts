import { BreadcrumbItem } from "../models/breadcrumb-item.model";
import { RowColumn } from "../models/row-column.model";
import { RowItem } from "../models/row-item.model";


export const BREADCRUMB_TELA_CONSULTA: BreadcrumbItem[] = [
    { titulo: "Consultar Contas", link: '/' }
];

export const BREADCRUMB_TELA_ABERTURA: BreadcrumbItem[] = [
    { titulo: "Consultar Contas", link: '' },
    { titulo: "Abertura de Conta", link: '/abertura' }
];

export const BREADCRUMB_TELA_DETALHES_CONTA: BreadcrumbItem[] = [
    { titulo: "Consultar Contas", link: '/' },
    { titulo: "Conta", link: '' }
];

export const COLUNAS_TELA_CONSULTA: RowColumn[] = [
    { name: 'ID', class: 'text-start' },
    { name: 'Cod. Agência', class: 'text-center' },
    { name: 'Nº Conta', class: 'text-center' },
    { name: 'Nome Titular', class: 'text-start' },
    { name: 'Tipo Titular', class: 'text-center' },
    { name: 'Identificação Titular', class: 'text-center' },
    { name: 'Ações', class: 'text-center' }
];

export const CAMPOS_JSON_CONSULTA: RowItem[] = [
    { name: 'id', class: 'text-start', action: false },
    { name: 'codigoAgencia', class: 'text-center', action: false },
    { name: 'numero', class: 'text-center', action: false },
    { name: 'nomeTitular', class: 'text-start', action: false },
    { name: 'tipoTitular', class: 'text-center', action: false },
    { name: 'documentoIdentificacaoTitular', class: 'text-center', action: false },
    { name: 'id', class: 'text-center', action: true }
];