import { Moment } from 'moment';

export interface ISearchQuery {
    id?: number;
    position?: string;
    location?: string;
    company?: string;
    queryDate?: Moment;
}

export class SearchQuery implements ISearchQuery {
    constructor(
        public id?: number,
        public position?: string,
        public location?: string,
        public company?: string,
        public queryDate?: Moment
    ) {}
}
