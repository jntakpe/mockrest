(function () {
    'use strict';

    angular.module('mockrest-core').directive('focusOn', function () {
        return {
            restrict: 'A',
            link: link
        };

        function link(scope, elem, attrs) {
            scope.$on('focusOn', function (e, name) {
                if (name === attrs.focusOn) {
                    return elem[0].focus();
                }
            });
        }
    });

})();