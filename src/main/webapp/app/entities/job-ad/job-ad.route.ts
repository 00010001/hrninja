import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { JobAd } from 'app/shared/model/job-ad.model';
import { JobAdService } from './job-ad.service';
import { JobAdComponent } from './job-ad.component';
import { JobAdDetailComponent } from './job-ad-detail.component';
import { JobAdUpdateComponent } from './job-ad-update.component';
import { JobAdDeletePopupComponent } from './job-ad-delete-dialog.component';
import { IJobAd } from 'app/shared/model/job-ad.model';

@Injectable({ providedIn: 'root' })
export class JobAdResolve implements Resolve<IJobAd> {
    constructor(private service: JobAdService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((jobAd: HttpResponse<JobAd>) => jobAd.body));
        }
        return of(new JobAd());
    }
}

export const jobAdRoute: Routes = [
    {
        path: 'job-ad',
        component: JobAdComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobAds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'job-ad/:id/view',
        component: JobAdDetailComponent,
        resolve: {
            jobAd: JobAdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobAds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'job-ad/new',
        component: JobAdUpdateComponent,
        resolve: {
            jobAd: JobAdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobAds'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'job-ad/:id/edit',
        component: JobAdUpdateComponent,
        resolve: {
            jobAd: JobAdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobAds'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const jobAdPopupRoute: Routes = [
    {
        path: 'job-ad/:id/delete',
        component: JobAdDeletePopupComponent,
        resolve: {
            jobAd: JobAdResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'JobAds'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
