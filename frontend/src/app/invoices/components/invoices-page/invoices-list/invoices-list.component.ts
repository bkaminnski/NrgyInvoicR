import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatPaginator, MatSort } from '@angular/material';
import { Invoice } from 'src/app/invoices/model/invoice.model';
import { InvoicesSearchCriteria } from 'src/app/invoices/model/invoices-search-criteria.model';
import { InvoicesListService } from './invoices-list.service';
import { InvoicesListDataSource } from './invoices-list.datasource';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { trigger, state, style, transition, animate } from '@angular/animations';

@Component({
  selector: 'app-invoices-list',
  templateUrl: './invoices-list.component.html',
  styleUrls: ['./invoices-list.component.scss'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({ height: '0px', minHeight: '0' })),
      state('expanded', style({ height: '*' })),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class InvoicesListComponent implements OnInit, AfterViewInit {
  private invoicesSearchCriteria: InvoicesSearchCriteria;
  public highlightedRowIndex: number;
  public dataSource: InvoicesListDataSource;
  public displayedColumns: string[] = [
    'number', 'issueDate', 'grossTotal',
    'client.number', 'client.lastName', 'client.meter.serialNumber',
    'invoiceRun.sinceClosed',
    'planVersion.plan.name',
    'options'
  ];
  public expandedInvoice: Invoice;

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

  mouseEnter(highlightedRowIndex: number) {
    this.highlightedRowIndex = highlightedRowIndex;
  }

  mouseLeave() {
    this.highlightedRowIndex = null;
  }
}
