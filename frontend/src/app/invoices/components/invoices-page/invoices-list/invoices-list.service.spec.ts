import * as moment from 'moment';

import { InvoicesListService } from './invoices-list.service';
import { InvoicesSearchCriteria } from '../../../model/invoices-search-criteria.model';

describe('InvoicesPageService', () => {

  const LOWER_BOUND = '2018-01-01T00:00:00.000Z';
  const UPPER_BOUND = '2018-01-31T00:00:00.000Z';
  const UPPER_BOUND_PLUS_ONE_DAY = '2018-02-01T00:00:00.000Z';

  let mockHttpClient;
  let service: InvoicesListService;

  beforeEach(() => {
    mockHttpClient = jasmine.createSpyObj('mockHttpClient', ['get']);
    service = new InvoicesListService(mockHttpClient);
  });

  it('should call correct endpoint', () => {
    service.findInvoices(new InvoicesSearchCriteria(), 'number', 'asc', 0, 10);

    expect(mockHttpClient.get).toHaveBeenCalledWith('/api/invoices', jasmine.any(Object));
  });

  it('should send no request params when dates range is unbounded', () => {
    mockHttpClient.get.and.callFake((url, params) => {
      expect(params.params.updates.length).toBe(4);
    });

    service.findInvoices(new InvoicesSearchCriteria(), 'number', 'asc', 0, 10);
  });

  it('should send only issueDateFrom request param when dates range is lower bound', () => {
    mockHttpClient.get.and.callFake((url, params) => {
      expect(params.params.updates.length).toBe(5);
      expect(params.params.updates[0].param).toBe('issueDateFrom');
      expect(params.params.updates[0].value).toBe(LOWER_BOUND);
    });

    service.findInvoices(new InvoicesSearchCriteria(moment(LOWER_BOUND)), 'number', 'asc', 0, 10);
  });

  it('should send only issueDateTo request param when dates range is upper bound', () => {
    mockHttpClient.get.and.callFake((url, params) => {
      expect(params.params.updates.length).toBe(5);
      expect(params.params.updates[0].param).toBe('issueDateTo');
      expect(params.params.updates[0].value).toBe(UPPER_BOUND_PLUS_ONE_DAY);
    });

    service.findInvoices(new InvoicesSearchCriteria(null, moment(UPPER_BOUND)), 'number', 'asc', 0, 10);
  });

  it('should send both issueDateFrom and issueDateTo request params when dates range is lower and upper bound', () => {
    mockHttpClient.get.and.callFake((url, params) => {
      expect(params.params.updates.length).toBe(6);
      expect(params.params.updates[0].param).toBe('issueDateFrom');
      expect(params.params.updates[0].value).toBe(LOWER_BOUND);
      expect(params.params.updates[1].param).toBe('issueDateTo');
      expect(params.params.updates[1].value).toBe(UPPER_BOUND_PLUS_ONE_DAY);
    });

    service.findInvoices(new InvoicesSearchCriteria(moment(LOWER_BOUND), moment(UPPER_BOUND)), 'number', 'asc', 0, 10);
  });
});
