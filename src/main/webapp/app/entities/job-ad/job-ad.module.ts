import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrninjaSharedModule } from 'app/shared';
import {
    JobAdComponent,
    JobAdDetailComponent,
    JobAdUpdateComponent,
    JobAdDeletePopupComponent,
    JobAdDeleteDialogComponent,
    jobAdRoute,
    jobAdPopupRoute
} from './';

const ENTITY_STATES = [...jobAdRoute, ...jobAdPopupRoute];

@NgModule({
    imports: [HrninjaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [JobAdComponent, JobAdDetailComponent, JobAdUpdateComponent, JobAdDeleteDialogComponent, JobAdDeletePopupComponent],
    entryComponents: [JobAdComponent, JobAdUpdateComponent, JobAdDeleteDialogComponent, JobAdDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HrninjaJobAdModule {}
