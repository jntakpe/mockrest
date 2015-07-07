(function () {
    'use strict';

    angular.module('mockrest-layout').filter('humanizeMenu', humanizeMenu);

    function humanizeMenu() {
        return function (menu) {
            if (!menu) {
                return;
            }
            if (menu.type === 'directive') {
                return menu.name.replace(/([A-Z])/g, function ($1) {
                    return '-' + $1.toLowerCase();
                });
            }
            return menu.label || menu.name;
        };
    }
})();