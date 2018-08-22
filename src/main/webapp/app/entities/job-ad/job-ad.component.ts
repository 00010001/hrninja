import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IJobAd } from 'app/shared/model/job-ad.model';
import { Principal } from 'app/core';
import { JobAdService } from './job-ad.service';

@Component({
    selector: 'jhi-job-ad',
    templateUrl: './job-ad.component.html'
})
export class JobAdComponent implements OnInit, OnDestroy {
    jobAds: IJobAd[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private jobAdService: JobAdService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.jobAdService.query().subscribe(
            (res: HttpResponse<IJobAd[]>) => {
                this.jobAds = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInJobAds();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IJobAd) {
        return item.id;
    }

    registerChangeInJobAds() {
        this.eventSubscriber = this.eventManager.subscribe('jobAdListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
