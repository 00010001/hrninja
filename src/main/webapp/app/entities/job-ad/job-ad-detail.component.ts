import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IJobAd } from 'app/shared/model/job-ad.model';

@Component({
    selector: 'jhi-job-ad-detail',
    templateUrl: './job-ad-detail.component.html'
})
export class JobAdDetailComponent implements OnInit {
    jobAd: IJobAd;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobAd }) => {
            this.jobAd = jobAd;
        });
    }

    previousState() {
        window.history.back();
    }
}
