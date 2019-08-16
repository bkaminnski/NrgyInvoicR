import { Directive, Injectable, forwardRef, Input } from '@angular/core';
import { AbstractControl, ValidationErrors, AsyncValidator, NG_ASYNC_VALIDATORS } from '@angular/forms';
import { Observable, of, timer, Subject } from 'rxjs';
import { map, catchError, mergeMap, tap } from 'rxjs/operators';
import { ExpressionService } from './expression.service';
import { ExpressionTestResult } from 'src/app/plans/model/expression-test-result.model';
import { FlattenedBucket } from 'src/app/plans/model/flattened-bucket.model';

@Injectable()
export class ExpressionTestResultValidator {
  constructor(private expressionService: ExpressionService) { }

  validate(control: AbstractControl, flattenedBucketsSubject: Subject<FlattenedBucket[]>): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    return timer(300).pipe(
      mergeMap(() => this.expressionService.testExpression(control.value)),
      tap(e => flattenedBucketsSubject.next(e.flattenedBuckets)),
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
export class ExpressionTestResultValidatorDirective implements AsyncValidator {
  @Input('appValidExpression') flattenedBucketsSubject: Subject<FlattenedBucket[]>;

  constructor(private validator: ExpressionTestResultValidator) { }

  validate(control: AbstractControl): Promise<ValidationErrors | null> | Observable<ValidationErrors | null> {
    return this.validator.validate(control, this.flattenedBucketsSubject);
  }
}
