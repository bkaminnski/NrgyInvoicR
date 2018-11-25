import * as moment from 'moment';

import { InvoicesPageService } from './invoices-page.service';
import { InvoicesSearchCriteria } from './invoices-search-criteria.model';

describe('InvoicesPageService', () => {

    const LOWER_BOUND = '2018-01-01T00:00:00.000Z';
    const UPPER_BOUND = '2018-01-31T23:59:59.999Z';

    let mockHttpClient;
    let service: InvoicesPageService;

    beforeEach(() => {
        mockHttpClient = jasmine.createSpyObj('mockHttpClient', ['get']);
        service = new InvoicesPageService(mockHttpClient);
    });

    it('should call correct endpoint', () => {
        service.findInvoices(new InvoicesSearchCriteria());

        expect(mockHttpClient.get).toHaveBeenCalledWith('/api/invoices', jasmine.any(Object));
    });

    it('should send no request params when dates range is unbounded', () => {
        mockHttpClient.get.and.callFake((url, params) => {
            expect(params.params.updates).toBeNull();
        });

        service.findInvoices(new InvoicesSearchCriteria());
    });

    it('should send only issueDateFrom request param when dates range is lower bound', () => {
        mockHttpClient.get.and.callFake((url, params) => {
            expect(params.params.updates.length).toBe(1);
            expect(params.params.updates[0].param).toBe('issueDateFrom');
            expect(params.params.updates[0].value).toBe(LOWER_BOUND);
        });

        service.findInvoices(new InvoicesSearchCriteria(moment(LOWER_BOUND)));
    });

    it('should send only issueDateTo request param when dates range is upper bound', () => {
        mockHttpClient.get.and.callFake((url, params) => {
            expect(params.params.updates.length).toBe(1);
            expect(params.params.updates[0].param).toBe('issueDateTo');
            expect(params.params.updates[0].value).toBe(UPPER_BOUND);
        });

        service.findInvoices(new InvoicesSearchCriteria(null, moment(UPPER_BOUND)));
    });

    it('should send both issueDateFrom and issueDateTo request params when dates range is lower and upper bound', () => {
        mockHttpClient.get.and.callFake((url, params) => {
            expect(params.params.updates.length).toBe(2);
            expect(params.params.updates[0].param).toBe('issueDateFrom');
            expect(params.params.updates[0].value).toBe(LOWER_BOUND);
            expect(params.params.updates[1].param).toBe('issueDateTo');
            expect(params.params.updates[1].value).toBe(UPPER_BOUND);
        });

        service.findInvoices(new InvoicesSearchCriteria(moment(LOWER_BOUND), moment(UPPER_BOUND)));
    });
});
