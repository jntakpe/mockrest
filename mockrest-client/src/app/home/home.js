'use strict';

import HomeCtrl from './home.controller';

export default angular
    .module('mockrest-home', ['mockrest-core'])
    .config($stateProvider => {
        $stateProvider.state('home', {
            url: '/',
            controller: HomeCtrl,
            controllerAs: 'home',
            templateUrl: 'app/home/home.html'
        });
    });