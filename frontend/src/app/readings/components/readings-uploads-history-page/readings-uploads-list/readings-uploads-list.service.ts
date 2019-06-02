import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ReadingUpload } from '../../../model/reading-upload.model';
import { ReadingsUploadsSearchCriteria } from '../../../model/readings-uploads-search-criteria.model';
import { Page } from 'src/app/core/model/page.model';

@Injectable()
export class ReadingsUploadsListService {

  constructor(private http: HttpClient) { }

  findReadingsUploads(readingsUploadsSearchCriteria: ReadingsUploadsSearchCriteria, sortColumn, sortDirection, pageIndex, pageSize): Observable<Page<ReadingUpload>> {
    let params = new HttpParams();
    if (readingsUploadsSearchCriteria.dateSince) {
      params = params.append('dateSince', readingsUploadsSearchCriteria.dateSince.toISOString());
    }
    if (readingsUploadsSearchCriteria.dateUntil) {
      params = params.append('dateUntil', readingsUploadsSearchCriteria.dateUntil.clone().add(1, 'day').toISOString());
    }
    if (readingsUploadsSearchCriteria.includeErrors) {
      params = params.append('includeErrors', JSON.stringify(true));
    }
    params = params
      .append('pageDefinition.sortColumn', sortColumn)
      .append('pageDefinition.sortDirection', sortDirection)
      .append('pageDefinition.pageNumber', pageIndex)
      .append('pageDefinition.pageSize', pageSize);
      console.log(params);
    return this.http.get<Page<ReadingUpload>>('/api/readingsUploads', { params: params });
  }
}
