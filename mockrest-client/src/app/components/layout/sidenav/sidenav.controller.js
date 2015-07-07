'use strict';

(function () {
    angular.module('mockrest-layout').controller('SidenavCtrl', SidenavCtrl);

    function SidenavCtrl(sidenavService, menuContent) {
        var vm = this;

        vm.menuContent = menuContent;
        vm.toggleOpen = toggleOpen;
        vm.isSelected = isSelected;
        vm.isSectionSelected = isSectionSelected;
        vm.isOpen = isOpen;

        function isSelected(page) {
            return sidenavService.isPageSelected(page);
        }

        function isOpen(section) {
            return sidenavService.isSectionSelected(section);
        }

        function toggleOpen(section) {
            sidenavService.toggleSelectSection(section);
        }

        function isSectionSelected(section) {
            return sidenavService.isSectionSelected(section);
        }
    }

})();