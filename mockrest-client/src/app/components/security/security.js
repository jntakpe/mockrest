'use strict';

import SigninCtrl from './signin/signin.controller';
import PrincipalService from './services/principal.service';
import OAuth2Service from './services/oauth2.service';
import AuthService from './services/auth.service';
import {authExpiredInterceptor, authInterceptor} from './interceptors/route.interceptor';

export default angular
    .module('mockrest-security', ['mockrest-core', 'LocalStorageModule'])
    .config(($stateProvider, $urlRouterProvider, $httpProvider, localStorageServiceProvider) => {
        $stateProvider.state('login', {
            url: '/login?logout',
            templateUrl: 'app/components/security/signin/signin.html',
            controller: SigninCtrl,
            controllerAs: 'signin'
        });
        $httpProvider.interceptors.push('authInterceptor');
        $httpProvider.interceptors.push('authExpiredInterceptor');
        localStorageServiceProvider.setPrefix('mockrest');
    })
    .run(($rootScope, $location, $window, $http, $state) => {
        $rootScope.$on('$stateChangeStart', (event, toState, toStateParams) => {
            $rootScope.toState = toState;
            $rootScope.toStateParams = toStateParams;
        });
        $rootScope.$on('$stateChangeSuccess', (event, toState, toParams, fromState, fromParams) => {
            $rootScope.previousStateName = fromState.name;
            $rootScope.previousStateParams = fromParams;
        });
        $rootScope.back = () => {
            if ($state.get($rootScope.previousStateName) === null) {
                $state.go('main.home');
            } else {
                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
            }
        };
    })
    .service('oauth2Service', OAuth2Service)
    .service('principalService', PrincipalService)
    .service('authService', AuthService)
    .factory('authInterceptor', authInterceptor)
    .factory('authExpiredInterceptor', authExpiredInterceptor);
