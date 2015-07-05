'use strict';

import HomeCtrl from './home.controller';

export default angular
    .module('mockrest-home', ['mockrest-core'])
    .config($stateProvider => {
        $stateProvider.state('main.home', {
            url: '/',
            views: {
                'content@layout': {
                    templateUrl: 'app/home/home.html',
                    controller: HomeCtrl,
                    controllerAs: 'home'
                }
            }
        });
    });