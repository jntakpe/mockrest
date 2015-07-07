'use strict';

(function () {
    angular.module('mockrest-error').controller('NotFoundCtrl', NotFoundCtrl);

    function NotFoundCtrl($state) {

        function goHomepage() {
            $state.go('main.home')
        }
    }
})();