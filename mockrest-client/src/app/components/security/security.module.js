(function () {
    'use strict';

    angular
        .module('mockrest-security', ['mockrest-core', 'LocalStorageModule'])
        .config(configState)
        .config(configInterceptors)
        .config(configLocalStorage)
        .run(run);

    function configState($stateProvider) {
        $stateProvider.state('login', {
            url: '/login?logout',
            templateUrl: 'app/components/security/signin/signin.html',
            controller: 'SignCtrl as signin'
        });
    }

    function configInterceptors($httpProvider) {
        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
    }

    function configLocalStorage(localStorageServiceProvider) {
        localStorageServiceProvider.setPrefix('mockrest');
    }

    function run($rootScope, $state) {
        $rootScope.$on('$stateChangeStart', handleChangeStart);
        $rootScope.$on('$stateChangeSuccess', handleChangeSuccess);
        $rootScope.back = goBack;

        function handleChangeStart(event, toState, toStateParams) {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;
        }

        function handleChangeSuccess(event, toState, toParams, fromState, fromParams) {
            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;
        }

        function goBack() {
            if ($state.get($rootScope.previousStateName) === null) {
                $state.go('main.home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        }
    }
})();