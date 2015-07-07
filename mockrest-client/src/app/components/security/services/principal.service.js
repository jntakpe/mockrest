(function () {
    'use strict';

    angular.module('mockrest-security').factory('principalService', principalService);

    var identity, authenticated;

    function principalService() {
        return {
            isIdentityResolved: isIdentityResolved,
            isAuthenticated: isAuthenticated,
            isInRole: isInRole,
            isInAnyRole: isInAnyRole,
            authenticate: authenticate,
            resolveIdentity: resolveIdentity
        };
    }

    function isIdentityResolved() {
        return identity !== null;
    }

    function isAuthenticated() {
        return authenticated;
    }

    function isInRole(role) {
        return authenticated && identity && identity.roles && identity.roles.indexOf(role) !== -1;
    }

    function isInAnyRole(roles) {
        if (!authenticated || !identity) {
            return false;
        }
        roles.forEach(function (role) {
            if (isInRole(role)) {
                return true;
            }
        });
        return false;
    }

    function authenticate(account) {
        identity = account;
        authenticated = identity !== null;
    }

    function resolveIdentity(force) {
        var deferred = $q.defer();
        if (force === true) {
            identity = null;
        }
        if (isIdentityResolved()) {
            deferred.resolve(identity);
            return deferred.promise;
        }
        $http.get('api/account').then(function (account) {
            identity = account.data;
            authenticated = true;
            deferred.resolve(identity);
        }).catch(function () {
            identity = null;
            authenticated = false;
            deferred.resolve(identity);
        });
        return deferred.promise;
    }

})();