/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrninjaTestModule } from '../../../test.module';
import { SearchQueryComponent } from 'app/entities/search-query/search-query.component';
import { SearchQueryService } from 'app/entities/search-query/search-query.service';
import { SearchQuery } from 'app/shared/model/search-query.model';

describe('Component Tests', () => {
    describe('SearchQuery Management Component', () => {
        let comp: SearchQueryComponent;
        let fixture: ComponentFixture<SearchQueryComponent>;
        let service: SearchQueryService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HrninjaTestModule],
                declarations: [SearchQueryComponent],
                providers: []
            })
                .overrideTemplate(SearchQueryComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SearchQueryComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SearchQueryService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SearchQuery(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.searchQueries[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
