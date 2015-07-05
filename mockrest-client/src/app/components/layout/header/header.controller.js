'use strict';

export default class HeaderCtrl {

    constructor($timeout, $mdSidenav, authorize) {
        this.$timeout = $timeout;
        this.$mdSidenav = $mdSidenav;
        if (authorize) {
            this.username = authorize.login;
        }
    }

    openMenu() {
        this.$timeout(() => this.$mdSidenav('menu-left').open());
    }

}
