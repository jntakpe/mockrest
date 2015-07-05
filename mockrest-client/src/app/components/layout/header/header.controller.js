'use strict';

export default class HeaderCtrl {

    constructor($timeout, $mdSidenav, $scope, HeaderService, authorize) {
        this.$timeout = $timeout;
        this.$mdSidenav = $mdSidenav;
        if (authorize) {
            this.username = authorize.login;
        }
        $scope.$on('$stateChangeSuccess', () => {
            this.breadcrumb = HeaderService.breadcrumbData();
        });
    }

    openMenu() {
        this.$timeout(() => this.$mdSidenav('menu-left').open());
    }

}
