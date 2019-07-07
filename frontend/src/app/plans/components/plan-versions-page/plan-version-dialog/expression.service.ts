import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ExpressionTestResult } from 'src/app/plans/model/expression-test-result.model';
import { Observable } from 'rxjs';

@Injectable()
export class ExpressionService {

  constructor(private http: HttpClient) { }

  testExpression(expression: string): Observable<ExpressionTestResult> {
    return this.http.post<ExpressionTestResult>('/api/expressions/tested', { expression: expression });
  }
}
