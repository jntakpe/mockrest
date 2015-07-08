(function () {
    'use strict';

    angular.module('mockrest-error').config(configState);

    function configState($stateProvider) {
        $stateProvider.state('notfound', {
            url: '/404',
            templateUrl: 'app/errors/404/notfound.html',
            controller: 'NotFoundCtrl as notFound'
        });
    }
})();