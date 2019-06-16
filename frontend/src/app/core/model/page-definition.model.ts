import { MatSort } from '@angular/material/sort';
import { MatPaginator } from '@angular/material/paginator';
import { HttpParams } from '@angular/common/http';

export class PageDefinition {

  public constructor(private sortColumn: string, private sortDirection: string, private pageNumber: number, private pageSize: number) { }

  public static forSortAndPaginator(sort: MatSort, paginator: MatPaginator) {
    return new PageDefinition(sort.active, sort.direction, paginator.pageIndex, paginator.pageSize);
  }

  public appendTo(params: HttpParams) {
    return params
      .append('pageDefinition.sortColumn', this.sortColumn)
      .append('pageDefinition.sortDirection', this.sortDirection)
      .append('pageDefinition.pageNumber', String(this.pageNumber))
      .append('pageDefinition.pageSize', String(this.pageSize));
  }
}
