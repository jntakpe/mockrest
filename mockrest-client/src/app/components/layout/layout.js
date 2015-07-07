(function () {
    'use strict';

    angular
        .module('mockrest-layout', ['mockrest-core', 'mockrest-security'])
        .config(function ($stateProvider) {
            $stateProvider.state('layout', {
                templateUrl: 'app/components/layout/layout.html'
            }).state('main', {
                parent: 'layout',
                abstract: true,
                views: {
                    'sidenav': {
                        templateUrl: 'app/components/layout/sidenav/sidenav.html',
                        controller: 'SidenavCtrl as sidenav'
                    },
                    'header': {
                        templateUrl: 'app/components/layout/header/header.html',
                        controller: 'HeaderCtrl as header'
                    }
                },
                resolve: {
                    authorize: function (authService, principalService) {
                        if (principalService.isAuthenticated()) {
                            return authService.authorize();
                        }
                        return {};
                    }
                }
            });
        })

})();