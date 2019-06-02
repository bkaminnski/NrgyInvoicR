import { Moment } from 'moment';

export class ReadingsUploadsSearchCriteria {

  constructor(public dateSince?: Moment, public dateUntil?: Moment, public includeErrors?: boolean) {

  }
}
