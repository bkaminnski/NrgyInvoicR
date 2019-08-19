import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';
import { InvoicesListService } from './invoices-list.service';
import { InvoicesListDataSource } from './invoices-list.datasource';
import { PageDefinition } from 'src/app/core/model/page-definition.model';

@Component({
  selector: 'app-invoices-list',
  templateUrl: './invoices-list.component.html',
  styleUrls: ['./invoices-list.component.scss']
})
export class InvoicesListComponent implements OnInit, AfterViewInit {
  private invoicesSearchCriteria: InvoicesSearchCriteria;
  dataSource: InvoicesListDataSource;
  displayedColumns: string[] = [
    'number', 'issueDate', 'grossTotal',
    'client.number', 'client.lastName', 'client.meter.serialNumber',
    'invoiceRun.sinceClosed',
    'planVersion.plan.name'
  ];

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
    this.sort.sortChange.subscribe(() => this.resetPaginatorAndSearchWithCriteria());
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search(invoicesSearchCriteria: InvoicesSearchCriteria) {
    this.invoicesSearchCriteria = invoicesSearchCriteria;
    this.resetPaginatorAndSearchWithCriteria();
  }

  private resetPaginatorAndSearchWithCriteria() {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria();
  }

  private searchWithCriteria() {
    this.dataSource.loadInvoices(this.invoicesSearchCriteria, PageDefinition.forSortAndPaginator(this.sort, this.paginator));
  }
}
