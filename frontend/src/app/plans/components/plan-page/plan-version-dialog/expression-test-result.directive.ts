import { Directive, Injectable, forwardRef } from '@angular/core';
import { AbstractControl, ValidationErrors, AsyncValidator, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { Observable, of, timer } from 'rxjs';
import { map, catchError, mergeMap } from 'rxjs/operators';
import { ExpressionService } from './expression.service';
import { ExpressionTestResult } from 'src/app/plans/model/expression-test-result.model';

@Injectable()
export class ExpressionTestResultValidator implements AsyncValidator {
  constructor(private expressionService: ExpressionService) { }

  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    return timer(300).pipe(
      mergeMap(() => this.expressionService.testExpression(control.value)),
      map(e => (e.valid ? null : { validExpression: this.toErrorMessage(e) })),
      catchError(() => of({ validExpression: 'Expression is invalid.' }))
    );
  }

  private toErrorMessage(expressionTestResult: ExpressionTestResult): string {
    if (expressionTestResult.valid) {
      return '';
    }

    if (expressionTestResult.coversWholeYear === false) {
      return 'Expression does not cover a full year.';
    } else if (expressionTestResult.lineError) {
      return 'Error in line ' + expressionTestResult.lineError.lineNumber + ': ' + expressionTestResult.lineError.errorMessage;
    }
  }
}

@Directive({
  selector: '[appValidExpression]',
  providers: [
    { provide: NG_ASYNC_VALIDATORS, useExisting: forwardRef(() => ExpressionTestResultValidatorDirective), multi: true },
    ExpressionTestResultValidator,
    ExpressionService
  ]
})
export class ExpressionTestResultValidatorDirective {
  constructor(private validator: ExpressionTestResultValidator) { }

  validate(control: AbstractControl) {
    return this.validator.validate(control);
  }
}
