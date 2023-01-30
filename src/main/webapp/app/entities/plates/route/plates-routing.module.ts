import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { PlatesComponent } from '../list/plates.component';
import { PlatesDetailComponent } from '../detail/plates-detail.component';
import { PlatesUpdateComponent } from '../update/plates-update.component';
import { PlatesRoutingResolveService } from './plates-routing-resolve.service';
import { ASC } from 'app/config/navigation.constants';

const platesRoute: Routes = [
  {
    path: '',
    component: PlatesComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PlatesDetailComponent,
    resolve: {
      plates: PlatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PlatesUpdateComponent,
    resolve: {
      plates: PlatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PlatesUpdateComponent,
    resolve: {
      plates: PlatesRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(platesRoute)],
  exports: [RouterModule],
})
export class PlatesRoutingModule {}
