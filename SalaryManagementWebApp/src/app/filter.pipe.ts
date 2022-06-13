import { Pipe, PipeTransform } from '@angular/core';
import { Employee } from './emp-info/emp.model';

@Pipe({
  name: 'filter'
})
export class FilterPipe implements PipeTransform {

  transform(value: any[], filterField: string): any[]{

    if (!filterField){
      return value;
    }

    return [];
  }

}
