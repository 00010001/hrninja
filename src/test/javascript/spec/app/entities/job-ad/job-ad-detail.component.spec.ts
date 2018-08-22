/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HrninjaTestModule } from '../../../test.module';
import { JobAdDetailComponent } from 'app/entities/job-ad/job-ad-detail.component';
import { JobAd } from 'app/shared/model/job-ad.model';

describe('Component Tests', () => {
    describe('JobAd Management Detail Component', () => {
        let comp: JobAdDetailComponent;
        let fixture: ComponentFixture<JobAdDetailComponent>;
        const route = ({ data: of({ jobAd: new JobAd(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HrninjaTestModule],
                declarations: [JobAdDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(JobAdDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(JobAdDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.jobAd).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
