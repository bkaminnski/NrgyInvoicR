import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Meter } from '../../../model/meter.model';
import { MetersSearchCriteria } from '../../../model/meters-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';

@Injectable()
export class MetersListService {

  constructor(private http: HttpClient) { }

  findMeters(metersSearchCriteria: MetersSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize): Observable<Page<Meter>> {
    let params = new HttpParams();
    if (metersSearchCriteria.serialNumber) {
      params = params.append('serialNumber', metersSearchCriteria.serialNumber);
    }
    params = params
      .append('pageDefinition.sortColumn', sortColumn)
      .append('pageDefinition.sortDirection', sortDirection)
      .append('pageDefinition.pageNumber', pageIndex)
      .append('pageDefinition.pageSize', pageSize);
    return this.http.get<Page<Meter>>('/api/meters', { params: params });
  }
}
