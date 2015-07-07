(function () {
    'use strict';

    angular.module('mockrest-layout').controller('HeaderCtrl', HeaderCtrl);


    function HeaderCtrl($scope, $timeout, $mdSidenav, headerService, authorize) {
        var vm = this;

        vm.openMenu = openMenu;

        if (authorize) {
            vm.username = authorize.login;
        }

        $scope.$on('$stateChangeSuccess', function refreshBreadcrumb() {
            vm.breadcrumb = headerService.breadcrumbData();
        });


        function openMenu() {
            $timeout(function () {
                $mdSidenav('menu-left').open();
            });
        }
    }

})();