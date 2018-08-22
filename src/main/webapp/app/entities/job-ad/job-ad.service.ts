import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IJobAd } from 'app/shared/model/job-ad.model';

type EntityResponseType = HttpResponse<IJobAd>;
type EntityArrayResponseType = HttpResponse<IJobAd[]>;

@Injectable({ providedIn: 'root' })
export class JobAdService {
    private resourceUrl = SERVER_API_URL + 'api/job-ads';

    constructor(private http: HttpClient) {}

    create(jobAd: IJobAd): Observable<EntityResponseType> {
        return this.http.post<IJobAd>(this.resourceUrl, jobAd, { observe: 'response' });
    }

    update(jobAd: IJobAd): Observable<EntityResponseType> {
        return this.http.put<IJobAd>(this.resourceUrl, jobAd, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IJobAd>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IJobAd[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
