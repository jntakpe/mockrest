'use strict';

(function () {
    angular.module('mockrest-error').controller('NotFoundCtrl', NotFoundCtrl);

    function NotFoundCtrl($state) {
        var vm = this;

        vm.goHomepage = goHomepage;

        function goHomepage() {
            $state.go('main.home');
        }
    }
})();