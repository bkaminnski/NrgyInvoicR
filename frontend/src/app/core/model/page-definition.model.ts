import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { HttpParams } from '@angular/common/http';

export class PageDefinition {

  constructor(private sort: MatSort, private paginator: MatPaginator) { }

  public appendTo(params: HttpParams) {
    return params
      .append('pageDefinition.sortColumn', this.sort.active)
      .append('pageDefinition.sortDirection', this.sort.direction)
      .append('pageDefinition.pageNumber', String(this.paginator.pageIndex))
      .append('pageDefinition.pageSize', String(this.paginator.pageSize));
  }
}
