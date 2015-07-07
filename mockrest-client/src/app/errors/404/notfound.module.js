(function () {
    'use strict';

    angular.module('mockrest-error').config(configState);

    function configState($stateProvider) {
        $stateProvider.state('404', {
            url: '/404',
            templateUrl: 'app/errors/404.html',
            controller: 'NotFoundCtrl as notFound'
        });
    }
})();