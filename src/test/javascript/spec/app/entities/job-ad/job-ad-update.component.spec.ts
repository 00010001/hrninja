/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { HrninjaTestModule } from '../../../test.module';
import { JobAdUpdateComponent } from 'app/entities/job-ad/job-ad-update.component';
import { JobAdService } from 'app/entities/job-ad/job-ad.service';
import { JobAd } from 'app/shared/model/job-ad.model';

describe('Component Tests', () => {
    describe('JobAd Management Update Component', () => {
        let comp: JobAdUpdateComponent;
        let fixture: ComponentFixture<JobAdUpdateComponent>;
        let service: JobAdService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HrninjaTestModule],
                declarations: [JobAdUpdateComponent]
            })
                .overrideTemplate(JobAdUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(JobAdUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(JobAdService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new JobAd(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobAd = entity;
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
                    const entity = new JobAd();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.jobAd = entity;
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
