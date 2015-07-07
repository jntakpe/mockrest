(function () {
    'use strict';

    angular.module('mockrest-security').factory('oauth2Service', oauth2Service);

    function oauth2Service($http, localStorageService) {
        return {
            login: login,
            logout: logout,
            getToken: getToken,
            hasValidToken: hasValidToken
        };

        function login(credentials) {
            var data = 'username=' + credentials.username + '&password=' + credentials.password +
                '&grant_type=password&scope=read%20xwrite&';
            return $http.post('oauth/token', data, {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                    'Accept': 'application/json',
                    'Authorization': 'Basic ' + window.btoa('mockrestserver' + ':' + 'mockrestsupersecret')
                }
            }).success(function (response) {
                var expiredAt = new Date();
                expiredAt.setSeconds(expiredAt.getSeconds() + response.expires_in);
                response.expires_at = expiredAt.getTime();
                localStorageService.set('token', response);
                return response;
            });
        }

        function logout() {
            $http.post('api/logout').then(function () {
                    localStorageService.clearAll();
                }
            );
        }

        function getToken() {
            return localStorageService.get('token');
        }

        function hasValidToken() {
            var token = getToken();
            return token && token.expires_at && token.expires_at > new Date().getTime();
        }
    }
})();
