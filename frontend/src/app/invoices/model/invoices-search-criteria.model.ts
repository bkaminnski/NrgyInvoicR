import { Moment } from 'moment';

export class InvoicesSearchCriteria {

  constructor(public issueDateSince?: Moment, public issueDateUntil?: Moment, public clientNumber?: string) { }
}
