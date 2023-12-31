import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SettingsComponent } from './settings.component';
import { Authority } from '../../config/authority.constants';

export const settingsRoute: Route = {
  path: 'settings',
  component: SettingsComponent,
  data: {
    pageTitle: 'global.menu.account.settings-title',
    authorities: [Authority.ADMIN, Authority.USER],
  },
  canActivate: [UserRouteAccessService],
};
