import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISearchQuery } from 'app/shared/model/search-query.model';

type EntityResponseType = HttpResponse<ISearchQuery>;
type EntityArrayResponseType = HttpResponse<ISearchQuery[]>;

@Injectable({ providedIn: 'root' })
export class SearchQueryService {
    private resourceUrl = SERVER_API_URL + 'api/search-queries';

    constructor(private http: HttpClient) {}

    create(searchQuery: ISearchQuery): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(searchQuery);
        return this.http
            .post<ISearchQuery>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(searchQuery: ISearchQuery): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(searchQuery);
        return this.http
            .put<ISearchQuery>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ISearchQuery>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ISearchQuery[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(searchQuery: ISearchQuery): ISearchQuery {
        const copy: ISearchQuery = Object.assign({}, searchQuery, {
            queryDate: searchQuery.queryDate != null && searchQuery.queryDate.isValid() ? searchQuery.queryDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.queryDate = res.body.queryDate != null ? moment(res.body.queryDate) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((searchQuery: ISearchQuery) => {
            searchQuery.queryDate = searchQuery.queryDate != null ? moment(searchQuery.queryDate) : null;
        });
        return res;
    }
}
