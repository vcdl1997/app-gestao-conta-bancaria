export class ObjectUtils {

    static isNotEmpty(value: any): boolean {
        if (value === null || value === undefined) {
            return false;
        }
      
        if (typeof value === 'string' && value.trim() === '') {
            return false;
        }
      
        if (typeof value === 'object' && Object.keys(value).length === 0) {
            return false;
        }
      
        return true;
    }
}