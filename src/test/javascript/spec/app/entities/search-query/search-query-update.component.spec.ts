/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HrninjaTestModule } from '../../../test.module';
import { SearchQueryUpdateComponent } from 'app/entities/search-query/search-query-update.component';
import { SearchQueryService } from 'app/entities/search-query/search-query.service';
import { SearchQuery } from 'app/shared/model/search-query.model';

describe('Component Tests', () => {
    describe('SearchQuery Management Update Component', () => {
        let comp: SearchQueryUpdateComponent;
        let fixture: ComponentFixture<SearchQueryUpdateComponent>;
        let service: SearchQueryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HrninjaTestModule],
                declarations: [SearchQueryUpdateComponent]
            })
                .overrideTemplate(SearchQueryUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SearchQueryUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchQueryService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SearchQuery(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.searchQuery = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SearchQuery();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.searchQuery = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
