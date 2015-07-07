(function () {
    'use strict';

    angular.module('mockrest-layout').factory('sidenavService', sidenavService);

    function sidenavService() {

        var openedSection, currentSection, currentPage;

        return {
            selectSection: selectSection,
            toggleSelectSection: toggleSelectSection,
            isSectionSelected: isSectionSelected,
            selectPage: selectPage,
            isPageSelected: isPageSelected
        };

        function selectSection(section) {
            openedSection = section;
        }

        function toggleSelectSection(section) {
            openedSection = (openedSection === section ? null : section);
        }

        function isSectionSelected(section) {
            return openedSection === section;
        }

        function selectPage(section, page) {
            currentSection = section;
            currentPage = page;
        }

        function isPageSelected(page) {
            return currentPage === page;
        }

    }
})();