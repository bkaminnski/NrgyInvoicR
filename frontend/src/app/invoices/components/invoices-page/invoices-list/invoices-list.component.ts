import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';
import { InvoicesListService } from './invoices-list.service';
import { InvoicesListDataSource } from './invoices-list.datasource';
import { MatPaginator, MatSort } from '@angular/material';

@Component({
  selector: 'app-invoices-list',
  templateUrl: './invoices-list.component.html',
  styleUrls: ['./invoices-list.component.scss']
})
export class InvoicesListComponent implements OnInit, AfterViewInit {

  private invoicesSearchCriteria: InvoicesSearchCriteria;
  dataSource: InvoicesListDataSource;
  displayedColumns: string[] = ['number', 'issueDate'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sortConfig = {
    initialSortActive: 'number',
    initialSortDirection: 'asc'
  };

  paginatorConfig = {
    initialPageIndex: 0,
    initialPageSize: 10,
    pageSizeOptions: [10, 100, 1000]
  };

  constructor(private invoicesListService: InvoicesListService) {
    this.dataSource = new InvoicesListDataSource(this.invoicesListService);
  }

  ngOnInit() { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => { this.paginator.pageIndex = 0; this.searchWithCriteria(); });
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search(invoicesSearchCriteria: InvoicesSearchCriteria) {
    this.invoicesSearchCriteria = invoicesSearchCriteria;
    this.searchWithCriteria();
  }

  private searchWithCriteria() {
    this.dataSource.loadInvoices(
      this.invoicesSearchCriteria,
      (this.sort.active) ? this.sort.active : this.sortConfig.initialSortActive,
      (this.sort.direction) ? this.sort.direction : this.sortConfig.initialSortDirection,
      (this.paginator.pageIndex) ? this.paginator.pageIndex : this.paginatorConfig.initialPageIndex,
      (this.paginator.pageSize) ? this.paginator.pageSize : this.paginatorConfig.initialPageSize
    );
  }
}
