import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { PlatesComponent } from './list/plates.component';
import { PlatesDetailComponent } from './detail/plates-detail.component';
import { PlatesUpdateComponent } from './update/plates-update.component';
import { PlatesDeleteDialogComponent } from './delete/plates-delete-dialog.component';
import { PlatesRoutingModule } from './route/plates-routing.module';
import { UploadImagesComponent } from './upload-images/upload-images.component';

@NgModule({
  imports: [SharedModule, PlatesRoutingModule],
  declarations: [PlatesComponent, PlatesDetailComponent, PlatesUpdateComponent, PlatesDeleteDialogComponent, UploadImagesComponent],
})
export class PlatesModule {}
