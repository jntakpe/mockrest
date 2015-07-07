(function () {
    'use strict';

    angular.module('mockrest-layout').controller('HeaderCtrl', HeaderCtrl);


    function HeaderCtrl($scope, $timeout, $mdSidenav, HeaderService, authorize) {
        var vm = this;

        if (authorize) {
            vm.username = authorize.login;
        }

        $scope.$on('$stateChangeSuccess', function refreshBreadcrumb() {
            vm.breadcrumb = HeaderService.breadcrumbData();
        });

        vm.openMenu = openMenu;

        function openMenu() {
            $timeout(function () {
                $mdSidenav('menu-left').open();
            });
        }
    }

})();