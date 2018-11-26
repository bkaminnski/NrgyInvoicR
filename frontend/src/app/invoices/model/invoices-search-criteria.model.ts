import { Moment } from 'moment';

export class InvoicesSearchCriteria {

    constructor(public issueDateFrom?: Moment, public issueDateTo?: Moment) {

    }
}
