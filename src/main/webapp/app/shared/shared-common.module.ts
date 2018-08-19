import { NgModule } from '@angular/core';

import { HrninjaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [HrninjaSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [HrninjaSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class HrninjaSharedCommonModule {}
