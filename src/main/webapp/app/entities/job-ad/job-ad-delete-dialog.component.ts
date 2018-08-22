import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IJobAd } from 'app/shared/model/job-ad.model';
import { JobAdService } from './job-ad.service';

@Component({
    selector: 'jhi-job-ad-delete-dialog',
    templateUrl: './job-ad-delete-dialog.component.html'
})
export class JobAdDeleteDialogComponent {
    jobAd: IJobAd;

    constructor(private jobAdService: JobAdService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.jobAdService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'jobAdListModification',
                content: 'Deleted an jobAd'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-job-ad-delete-popup',
    template: ''
})
export class JobAdDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ jobAd }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(JobAdDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.jobAd = jobAd;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
