import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISearchQuery } from 'app/shared/model/search-query.model';
import { SearchQueryService } from './search-query.service';

@Component({
    selector: 'jhi-search-query-update',
    templateUrl: './search-query-update.component.html'
})
export class SearchQueryUpdateComponent implements OnInit {
    private _searchQuery: ISearchQuery;
    isSaving: boolean;
    queryDateDp: any;

    constructor(private searchQueryService: SearchQueryService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ searchQuery }) => {
            this.searchQuery = searchQuery;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.searchQuery.id !== undefined) {
            this.subscribeToSaveResponse(this.searchQueryService.update(this.searchQuery));
        } else {
            this.subscribeToSaveResponse(this.searchQueryService.create(this.searchQuery));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISearchQuery>>) {
        result.subscribe((res: HttpResponse<ISearchQuery>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get searchQuery() {
        return this._searchQuery;
    }

    set searchQuery(searchQuery: ISearchQuery) {
        this._searchQuery = searchQuery;
    }
}
