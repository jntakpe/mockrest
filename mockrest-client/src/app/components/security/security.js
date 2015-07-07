(function () {
    'use strict';

    angular
        .module('mockrest-security', ['mockrest-core', 'LocalStorageModule'])
        .config(function ($stateProvider, $httpProvider, localStorageServiceProvider) {
            $stateProvider.state('login', {
                url: '/login?logout',
                templateUrl: 'app/components/security/signin/signin.html',
                controller: 'SignCtrl as signin'
            });
            $httpProvider.interceptors.push('authInterceptor');
            $httpProvider.interceptors.push('authExpiredInterceptor');
            localStorageServiceProvider.setPrefix('mockrest');
        })
        .run(function ($rootScope, $state) {
            $rootScope.$on('$stateChangeStart', function (event, toState, toStateParams) {
                $rootScope.toState = toState;
                $rootScope.toStateParams = toStateParams;
            });
            $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
                $rootScope.previousStateName = fromState.name;
                $rootScope.previousStateParams = fromParams;
            });
            $rootScope.back = function () {
                if ($state.get($rootScope.previousStateName) === null) {
                    $state.go('main.home');
                } else {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            };
        })
})();