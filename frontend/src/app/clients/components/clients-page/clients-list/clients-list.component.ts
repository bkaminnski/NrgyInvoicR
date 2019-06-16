import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { MatSort, MatPaginator, MatDialog, MatDialogConfig } from '@angular/material';
import { ClientsService } from '../clients.service';
import { ClientsListDataSource } from './clients-list.datasource';
import { ClientsSearchCriteria } from 'src/app/clients/model/clients-search-criteria.model';
import { Client } from 'src/app/clients/model/client.model';
import { ClientDialogComponent } from '../client-dialog/client-dialog.component';
import { Page } from 'src/app/core/model/page.model';
import { PageDefinition } from 'src/app/core/model/page-definition.model';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-clients-list',
  templateUrl: './clients-list.component.html',
  styleUrls: ['./clients-list.component.scss']
})
export class ClientsListComponent implements OnInit, AfterViewInit {
  private clientsSearchCriteria: ClientsSearchCriteria;
  public highlightedRowIndex: number;
  public spotlightedRowIndex: number;
  public dataSource: ClientsListDataSource;
  public displayedColumns: string[] = ['number', 'firstName', 'lastName', 'addressLine1', 'postalCode', 'city', 'createdDate', 'options'];

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  sortConfig = {
    initialSortActive: 'createdDate',
    initialSortDirection: 'desc'
  };

  paginatorConfig = {
    initialPageIndex: 0,
    initialPageSize: 10,
    pageSizeOptions: [10, 100, 1000]
  };

  constructor(private clientsService: ClientsService, private dialog: MatDialog) {
    this.dataSource = new ClientsListDataSource(this.clientsService);
    this.clientsSearchCriteria = new ClientsSearchCriteria();
  }

  ngOnInit() { }

  ngAfterViewInit() {
    this.sort.sortChange.subscribe(() => this.resetPaginatorAndSearchWithCriteria());
    this.paginator.page.subscribe(() => this.searchWithCriteria());
  }

  search(clientsSearchCriteria: ClientsSearchCriteria) {
    this.clientsSearchCriteria = clientsSearchCriteria;
    this.resetPaginatorAndSearchWithCriteria();
  }

  private resetPaginatorAndSearchWithCriteria(client: Client = null) {
    this.paginator.pageIndex = 0;
    this.searchWithCriteria(client);
  }

  private searchWithCriteria(client: Client = null) {
    this.dataSource.loadClients(
      this.clientsSearchCriteria,
      PageDefinition.forSortAndPaginator(this.sort, this.paginator),
      (page: Page<Client>) => this.spotlightClientOnPage(page, client)
    );
  }

  private spotlightClientOnPage(page: Page<Client>, client: Client) {
    if (client !== null) {
      this.spotlightedRowIndex = page.indexOf(client);
      setTimeout(() => this.spotlightedRowIndex = null, 1000);
    }
  }

  registerClient() {
    const client = this.dataSource.totalElements === 0 ? Client.fromClientsSearchCriteria(this.clientsSearchCriteria) : new Client();
    this.openClientDialog(client).subscribe(m => {
      if (m) {
        this.clientsSearchCriteria.reset();
        this.resetPaginatorAndSearchWithCriteria(m);
      }
    });
  }

  editClient(client: Client) {
    this.openClientDialog(client).subscribe(m => {
      if (m) {
        this.searchWithCriteria(m);
      }
    });
  }

  openClientDialog(client: Client): Observable<Client> {
    const dialogConfig: MatDialogConfig<Client> = {
      data: client,
      disableClose: true,
      autoFocus: true,
      minWidth: '50%',
      maxWidth: '70%'
    };
    return this.dialog.open(ClientDialogComponent, dialogConfig).afterClosed();
  }

  mouseEnter(highlightedRowIndex: number) {
    this.highlightedRowIndex = highlightedRowIndex;
  }

  mouseLeave() {
    this.highlightedRowIndex = null;
  }
}
