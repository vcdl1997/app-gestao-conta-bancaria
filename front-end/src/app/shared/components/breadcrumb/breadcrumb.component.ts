import { Component, Input } from '@angular/core';
import { BreadcrumbItem } from '../../../core/models/breadcrumb-item.model';
import { Location } from '@angular/common';

@Component({
  selector: 'breadcrumb',
  templateUrl: './breadcrumb.component.html',
  styleUrl: './breadcrumb.component.css'
})
export class BreadcrumbComponent {

	@Input() breadcrumbItems: BreadcrumbItem[] = [];

	constructor(private location: Location) { }

	formatterLink(link: string){
		return link ? link : this.location.path();
	}
}
