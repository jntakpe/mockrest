(function () {
    'use strict';

    angular.module('mockrest-security').factory('authInterceptor', authInterceptor);

    function authInterceptor($q, $injector, localStorageService) {
        return {
            responseError: responseError,
            authInterceptor: authInterceptor
        };

        function responseError(response) {
            if (response.status === 401 && (response.data.error === 'invalid_token' || response.data.error === 'Unauthorized')) {
                localStorageService.remove('token');
                var principalService = $injector.get('principalService');
                if (principalService.isAuthenticated()) {
                    var authService = $injector.get('authService');
                    authService.authorize(true);
                }
            }
            return $q.reject(response);
        }

        function authInterceptor(localStorageService) {
            return {
                request: function (config) {
                    config.headers = config.headers || {};
                    var token = localStorageService.get('token');
                    if (token && token.expires_at && token.expires_at > new Date().getTime()) {
                        config.headers.Authorization = 'Bearer ' + token.access_token;
                    }
                    return config;
                }
            };
        }

    }
})();