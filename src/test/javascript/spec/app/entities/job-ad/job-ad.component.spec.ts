/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { HrninjaTestModule } from '../../../test.module';
import { JobAdComponent } from 'app/entities/job-ad/job-ad.component';
import { JobAdService } from 'app/entities/job-ad/job-ad.service';
import { JobAd } from 'app/shared/model/job-ad.model';

describe('Component Tests', () => {
    describe('JobAd Management Component', () => {
        let comp: JobAdComponent;
        let fixture: ComponentFixture<JobAdComponent>;
        let service: JobAdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HrninjaTestModule],
                declarations: [JobAdComponent],
                providers: []
            })
                .overrideTemplate(JobAdComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobAdComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobAdService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new JobAd(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.jobAds[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
