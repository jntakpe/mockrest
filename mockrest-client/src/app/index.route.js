(function () {
    'use strict';

    angular.module('fra').config(routeConfig);

    function routeConfig($urlRouterProvider) {
        $urlRouterProvider.otherwise('/404');
    }

})();