import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISearchQuery } from 'app/shared/model/search-query.model';
import { SearchQueryService } from './search-query.service';

@Component({
    selector: 'jhi-search-query-delete-dialog',
    templateUrl: './search-query-delete-dialog.component.html'
})
export class SearchQueryDeleteDialogComponent {
    searchQuery: ISearchQuery;

    constructor(
        private searchQueryService: SearchQueryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.searchQueryService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'searchQueryListModification',
                content: 'Deleted an searchQuery'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-search-query-delete-popup',
    template: ''
})
export class SearchQueryDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ searchQuery }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SearchQueryDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.searchQuery = searchQuery;
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
