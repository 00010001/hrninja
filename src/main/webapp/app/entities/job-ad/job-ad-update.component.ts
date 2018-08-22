import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IJobAd } from 'app/shared/model/job-ad.model';
import { JobAdService } from './job-ad.service';

@Component({
    selector: 'jhi-job-ad-update',
    templateUrl: './job-ad-update.component.html'
})
export class JobAdUpdateComponent implements OnInit {
    private _jobAd: IJobAd;
    isSaving: boolean;

    constructor(private jobAdService: JobAdService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ jobAd }) => {
            this.jobAd = jobAd;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.jobAd.id !== undefined) {
            this.subscribeToSaveResponse(this.jobAdService.update(this.jobAd));
        } else {
            this.subscribeToSaveResponse(this.jobAdService.create(this.jobAd));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IJobAd>>) {
        result.subscribe((res: HttpResponse<IJobAd>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get jobAd() {
        return this._jobAd;
    }

    set jobAd(jobAd: IJobAd) {
        this._jobAd = jobAd;
    }
}
