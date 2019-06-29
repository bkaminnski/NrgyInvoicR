import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { MarketingName } from '../../../model/marketing-name.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Injectable()
export class MarketingNamesListService {

  constructor(private http: HttpClient) { }

  findMarketingNames(pageDefinition: PageDefinition): Observable<Page<MarketingName>> {
    let params = new HttpParams();
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<MarketingName>>('/api/marketingNames', { params: params });
  }
}
