(function () {
    'use strict';

    angular.module('mockrest-security').factory('authInterceptor', authInterceptor);

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

})();