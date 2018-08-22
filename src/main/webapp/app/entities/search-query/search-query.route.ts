import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SearchQuery } from 'app/shared/model/search-query.model';
import { SearchQueryService } from './search-query.service';
import { SearchQueryComponent } from './search-query.component';
import { SearchQueryDetailComponent } from './search-query-detail.component';
import { SearchQueryUpdateComponent } from './search-query-update.component';
import { SearchQueryDeletePopupComponent } from './search-query-delete-dialog.component';
import { ISearchQuery } from 'app/shared/model/search-query.model';

@Injectable({ providedIn: 'root' })
export class SearchQueryResolve implements Resolve<ISearchQuery> {
    constructor(private service: SearchQueryService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((searchQuery: HttpResponse<SearchQuery>) => searchQuery.body));
        }
        return of(new SearchQuery());
    }
}

export const searchQueryRoute: Routes = [
    {
        path: 'search-query',
        component: SearchQueryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SearchQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'search-query/:id/view',
        component: SearchQueryDetailComponent,
        resolve: {
            searchQuery: SearchQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SearchQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'search-query/new',
        component: SearchQueryUpdateComponent,
        resolve: {
            searchQuery: SearchQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SearchQueries'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'search-query/:id/edit',
        component: SearchQueryUpdateComponent,
        resolve: {
            searchQuery: SearchQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SearchQueries'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const searchQueryPopupRoute: Routes = [
    {
        path: 'search-query/:id/delete',
        component: SearchQueryDeletePopupComponent,
        resolve: {
            searchQuery: SearchQueryResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SearchQueries'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
