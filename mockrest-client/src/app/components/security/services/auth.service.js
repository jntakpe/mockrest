(function () {
    'use strict';

    angular.module('mockrest-security').factory('authService', authService);

    function authService($rootScope, $state, $q, principalService, oauth2Service) {
        return {
            login: login,
            logout: logout,
            authorize: authorize
        };

        function login(credentials, callback) {
            var cb = callback || angular.noop, deferred = $q.defer();
            oauth2Service.login(credentials).then(function (response) {
                    principalService.resolveIdentity(true).then(function () {
                        deferred.resolve(response)
                    });
                    return cb();
                }
            ).catch(function (err) {
                    logout();
                    deferred.reject(err);
                    return cb(err);
                }
            );
            return deferred.promise;
        }

        function logout() {
            oauth2Service.logout();
            principalService.authenticate(null);
        }

        function authorize(force) {
            return principalService.resolveIdentity(force).then(function (identity) {
                    var data = $rootScope.toState.data;
                    if (data && data.roles && data.roles.length > 0 && !principalService.isInAnyRole(data.roles)) {
                        if (principalService.isAuthenticated()) {
                            $state.go('main.home');
                        } else {
                            $rootScope.returnToState = $rootScope.toState;
                            $rootScope.returnToStateParams = $rootScope.toStateParams;
                            $state.go('login');
                        }
                    }
                    return identity;
                }
            );
        }
    }
})();