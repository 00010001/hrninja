import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HrninjaSharedModule } from 'app/shared';
import {
    SearchQueryComponent,
    SearchQueryDetailComponent,
    SearchQueryUpdateComponent,
    SearchQueryDeletePopupComponent,
    SearchQueryDeleteDialogComponent,
    searchQueryRoute,
    searchQueryPopupRoute
} from './';

const ENTITY_STATES = [...searchQueryRoute, ...searchQueryPopupRoute];

@NgModule({
    imports: [HrninjaSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SearchQueryComponent,
        SearchQueryDetailComponent,
        SearchQueryUpdateComponent,
        SearchQueryDeleteDialogComponent,
        SearchQueryDeletePopupComponent
    ],
    entryComponents: [SearchQueryComponent, SearchQueryUpdateComponent, SearchQueryDeleteDialogComponent, SearchQueryDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HrninjaSearchQueryModule {}
