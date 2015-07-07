(function () {
    'use strict';

    angular.module('mockrest-security').factory('authExpiredInterceptor', authExpiredInterceptor);


    function authExpiredInterceptor($q, $injector, localStorageService) {
        return {
            responseError: function (response) {
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
        };
    }

})();