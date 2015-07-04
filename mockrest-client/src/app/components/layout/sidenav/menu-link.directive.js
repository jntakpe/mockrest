'use strict';

export default function () {
    return {
        scope: {
            section: '='
        },
        templateUrl: 'app/components/layout/sidenav/menu-link.tmpl.html',
        link: function ($scope, $element) {
            var controller = $element.parent().controller();
            $scope.isSelected = function () {
                return controller.isSelected($scope.section);
            };
            $scope.focusSection = function () {
                controller.autoFocusContent = true;
            };
        }
    };
};