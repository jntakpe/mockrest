'use strict';

import SidenavCtrl from './sidenav/sidenav.controller';
import SidenavService from './sidenav/sidenav.service';
import humanizeDoc from './sidenav/sidenav.filter';
import HeaderCtrl from './header/header.controller';
import HeaderService from './header/header.service';
import menuLink from './sidenav/menu-link.directive';
import menuToggle from './sidenav/menu-toggle.directive';

export default angular
    .module('mockrest-layout', ['mockrest-core', 'mockrest-security'])
    .config($stateProvider => {
        $stateProvider.state('layout', {
            templateUrl: 'app/components/layout/layout.html'
        }).state('main', {
            parent: 'layout',
            abstract: true,
            views: {
                'sidenav': {
                    templateUrl: 'app/components/layout/sidenav/sidenav.html',
                    controller: SidenavCtrl,
                    controllerAs: 'sidenav'
                },
                'header': {
                    templateUrl: 'app/components/layout/header/header.html',
                    controller: HeaderCtrl,
                    controllerAs: 'header'
                }
            },
            resolve: {
                authorize: (authService, principalService) => {
                    if (principalService.isAuthenticated()) {
                        return authService.authorize();
                    }
                    return {};
                }
            }
        });
    })
    .filter('humanizeDoc', humanizeDoc)
    .service('SidenavService', SidenavService)
    .service('HeaderService', HeaderService)
    .directive('menuLink', menuLink)
    .directive('menuToggle', menuToggle);
