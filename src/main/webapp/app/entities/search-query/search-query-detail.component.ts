import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISearchQuery } from 'app/shared/model/search-query.model';

@Component({
    selector: 'jhi-search-query-detail',
    templateUrl: './search-query-detail.component.html'
})
export class SearchQueryDetailComponent implements OnInit {
    searchQuery: ISearchQuery;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ searchQuery }) => {
            this.searchQuery = searchQuery;
        });
    }

    previousState() {
        window.history.back();
    }
}
