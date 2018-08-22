import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HrninjaSearchQueryModule } from './search-query/search-query.module';
import { HrninjaJobAdModule } from './job-ad/job-ad.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HrninjaSearchQueryModule,
        HrninjaJobAdModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HrninjaEntityModule {}
