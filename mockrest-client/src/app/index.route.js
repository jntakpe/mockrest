(function () {
    'use strict';

    angular.module('mockrest').config(routeConfig);

    function routeConfig($urlRouterProvider) {
        $urlRouterProvider.otherwise('/404');
    }

})();