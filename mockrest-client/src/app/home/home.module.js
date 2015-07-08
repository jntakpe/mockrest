(function () {
    'use strict';

    angular.module('mockrest-home', ['mockrest-core'])
        .config(function ($stateProvider) {
            $stateProvider.state('main.home', {
                url: '/',
                data: {
                    title: 'Accueil'
                },
                views: {
                    'content@layout': {
                        templateUrl: 'app/home/home.html',
                        controller: 'HomeCtrl as home'
                    }
                }
            });
        });
})();