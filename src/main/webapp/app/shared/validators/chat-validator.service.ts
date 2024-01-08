import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export class ChatValidatorService {
  noWhitespace(): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      if (control.value === '' || control.value.trim().length !== 0) {
        return null;
      }
      return { whitespace: true } as ValidationErrors;
    };
  }
}
