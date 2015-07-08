(function () {
    'use strict';

    angular.module('mockrest-security').controller('SignCtrl', SignCtrl);

    function SignCtrl($rootScope, $state, $stateParams, toastr, authService) {
        var vm = this;

        vm.login = login;
        vm.user = {};


        if ($stateParams.logout) {
            toastr.success('Successfully logged out');
        }

        authService.logout();

        function login() {
            authService.login({
                username: vm.user.username,
                password: vm.user.password
            }).then(function () {
                if ($rootScope.previousStateName === 'register') {
                    $state.go('main.home'); //TODO when api view created redirect to it
                } else {
                    $rootScope.back();
                }
            }).catch(function () {
                vm.user = {};
                toastr.error('Invalid credentials');
            });
        }
    }

})();