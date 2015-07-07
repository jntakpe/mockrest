(function () {
    'use strict';

    angular.module('mockrest-layout').directive('menuLink', menuLink);

    function menuLink() {

        return {
            scope: {
                section: '='
            },
            templateUrl: 'app/components/layout/sidenav/menu-link.tmpl.html',
            link: link
        };

        function link(scope, element) {
            var controller = element.parent().controller();
            scope.isSelected = function () {
                return controller.isSelected(scope.section);
            };
            scope.focusSection = function () {
                controller.autoFocusContent = true;
            };
        }

    }
})();