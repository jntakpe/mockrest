(function () {
    'use strict';

    angular.module('mockrest-layout').factory('headerService', headerService);

    function headerService($state) {

        return {
            breadcrumbData: breadcrumbData
        };

        function parentsData(currentStateData) {
            var breadcrumb = [];
            currentStateData.breadcrumb = currentStateData.breadcrumb || [];
            currentStateData.breadcrumb.forEach(function (stateName) {
                breadcrumb.push({
                    title: $state.get(stateName).data.title,
                    name: stateName
                });
            });
            return breadcrumb;
        }

        function breadcrumbData() {
            if ($state.$current.data) {
                return {
                    title: $state.$current.data.title,
                    parents: parentsData($state.$current.data)
                };
            }
            return {};
        }
    }
})();
