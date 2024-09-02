import { Component, Input, OnDestroy, OnInit } from '@angular/core';

@Component({
  selector: 'navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit, OnDestroy{

	@Input() nomeTitular: string = "";
	@Input() saldo: string = "";
	verSaldo: boolean = false;

	constructor() {}

	ngOnInit(): void{
		this.nomeTitular = "";
	}

	ngOnDestroy(): void{
		this.nomeTitular = "";
	}

	visaoSaldo(){
		this.verSaldo = !this.verSaldo;
	}

}
