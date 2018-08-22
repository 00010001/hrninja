/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrninjaTestModule } from '../../../test.module';
import { SearchQueryDetailComponent } from 'app/entities/search-query/search-query-detail.component';
import { SearchQuery } from 'app/shared/model/search-query.model';

describe('Component Tests', () => {
    describe('SearchQuery Management Detail Component', () => {
        let comp: SearchQueryDetailComponent;
        let fixture: ComponentFixture<SearchQueryDetailComponent>;
        const route = ({ data: of({ searchQuery: new SearchQuery(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HrninjaTestModule],
                declarations: [SearchQueryDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SearchQueryDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SearchQueryDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.searchQuery).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
