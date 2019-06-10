import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Meter } from '../../model/meter.model';
import { MetersSearchCriteria } from '../../model/meters-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Injectable()
export class MetersService {

  constructor(private http: HttpClient) { }

  createMeter(meter: Meter): Observable<Meter> {
    return this.http.post<Meter>('/api/meters', meter);
  }

  findMeters(metersSearchCriteria: MetersSearchCriteria, pageDefinition: PageDefinition): Observable<Page<Meter>> {
    let params = new HttpParams();
    if (metersSearchCriteria.serialNumber) {
      params = params.append('serialNumber', metersSearchCriteria.serialNumber);
    }
    params = pageDefinition.appendTo(params);
    return this.http.get<Page<Meter>>('/api/meters', { params: params });
  }
}
