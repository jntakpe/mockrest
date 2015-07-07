'use strict';

(function () {
    angular.module('mockrest-layout').controller('SidenavCtrl', SidenavCtrl);

    function SidenavCtrl(sidenavService, menuContent) {
        var vm = this;

        vm.menuContent = menuContent;

        vm.isSectionSelected = function (section) {
            var selected = false, openedSection = sidenavService.openedSection;
            if (openedSection === section) {
                selected = true;
            }
            else if (section.children) {
                section.children.forEach(function (childSection) {
                    if (childSection === openedSection) {
                        selected = true;
                    }
                });
            }
            return selected;
        };

        vm.isSelected = function (page) {
            return sidenavService.isPageSelected(page);
        };

        vm.isOpen = function (section) {
            return sidenavService.isSectionSelected(section);
        };

        vm.toggleOpen = function (section) {
            sidenavService.toggleSelectSection(section);
        };
    }

})();