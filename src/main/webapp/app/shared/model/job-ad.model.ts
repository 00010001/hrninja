export interface IJobAd {
    id?: number;
    title?: string;
    url?: string;
    searchQueryId?: number;
}

export class JobAd implements IJobAd {
    constructor(public id?: number, public title?: string, public url?: string, public searchQueryId?: number) {}
}
