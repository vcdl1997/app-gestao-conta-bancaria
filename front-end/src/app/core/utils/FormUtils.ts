import { ObjectUtils } from "./ObjectUtils";

const { isNotEmpty } = ObjectUtils;

export class FormUtils {

    static inputNumber(event: any) {
        const allowedRegex = /[0-9]/g;

        if (event.key != undefined && !event.key.match(allowedRegex)) {
            event.preventDefault();
        }

        event.target.value = event.target.value.replace(/\D/g, '');
    }

    static monetary(event: any): string {
        const value: string = (event?.target?.value ?? '').replace(/\D/g, '');
        const monetaryUnit = value.length > 2 ? value.slice(0, -2) : value;
        const monetarySubunit = value.length <= 2 ? '00' : `${value.slice(-2).length == 1 ? '0' + value.slice(-2) : value.slice(-2) }`;
        return `${monetaryUnit}.${monetarySubunit}`;
    }

    static serialize(json: any): string {
        return '?' + Object.keys(json)
            .filter((key) => isNotEmpty(`${json[key]}`))
            .map((key) => json[key] ? `${key}=${json[key]}` : '')
            .join('&')
        ;
    }
}