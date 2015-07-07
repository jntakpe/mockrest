(function () {
    'use strict';

    angular.module('mockrest-security').controller('SignCtrl', SignCtrl);

    function SignCtrl($rootScope, $state, $stateParams, $mdToast, authService) {
        var vm = this;

        authService.logout();
        if ($stateParams.logout) {
            this.$mdToast.show(this.$mdToast.simple().content('Logged out successfully').position('top'));
        }

        vm.login = function () {
            authService.login({
                username: vm.user.username,
                password: vm.user.password
            }).then(function () {
                error = false;
                if ($rootScope.previousStateName === 'register') {
                    $state.go('main.home');
            } else {
                    $rootScope.back();
            }
            }, function () {
                vm.user = {};
                $mdToast.show($mdToast.simple().content('Invalid credentials').position('top'));
        });
        }
    }

})();