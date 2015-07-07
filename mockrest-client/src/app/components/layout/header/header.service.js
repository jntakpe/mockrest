(function () {
    'use strict';

    angular.module('mockrest-layout').factory('headerService', headerService);

    function headerService($state) {

        return {
            breadcrumbData: breadcrumbData
        };

        function currentData() {
            return $state.$current.data;
        }

        function parentsData() {
            var breadcrumb = [];
            currentData().forEach(function (stateName) {
                breadcrumb.push({
                    title: $state.get(stateName).data.title,
                    name: stateName
                });
            });
            return breadcrumb;
        }

        function breadcrumbData() {
            if (currentData()) {
                return {
                    title: currentData().title,
                    parents: parentsData()
                }
            }
            return {};
        }
    }
})();
