/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { HrninjaTestModule } from '../../../test.module';
import { SearchQueryDeleteDialogComponent } from 'app/entities/search-query/search-query-delete-dialog.component';
import { SearchQueryService } from 'app/entities/search-query/search-query.service';

describe('Component Tests', () => {
    describe('SearchQuery Management Delete Component', () => {
        let comp: SearchQueryDeleteDialogComponent;
        let fixture: ComponentFixture<SearchQueryDeleteDialogComponent>;
        let service: SearchQueryService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HrninjaTestModule],
                declarations: [SearchQueryDeleteDialogComponent]
            })
                .overrideTemplate(SearchQueryDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SearchQueryDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchQueryService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
