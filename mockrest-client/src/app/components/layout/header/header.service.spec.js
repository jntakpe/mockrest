(function () {
    'use strict';

    describe('Service: headerService =>', function () {

        var headerService, $state, $rootScope, breacrumb = ['parent'], parentTitle = 'Parent', testTitle = 'Test';

        beforeEach(function () {
            module('mockrest');
            module(function ($stateProvider) {
                $stateProvider.state('parent', {
                    url: '/parent',
                    data: {
                        title: parentTitle
                    }
                }).state('parent.test', {
                    url: '/test',
                    data: {
                        breadcrumb: breacrumb,
                        title: testTitle
                    }
                }).state('nodata', {
                    url: '/nodata'
                });
            });

            inject(function (_headerService_, _$state_, _$rootScope_) {
                headerService = _headerService_;
                $state = _$state_;
                $rootScope = _$rootScope_;
            });
        });

        it('should exist', function () {
            expect(!!headerService).toBe(true);
        });

        it('should has current state with data', function () {
            $state.go('parent');
            $rootScope.$apply();
            expect($state.$current).toBeDefined();
            expect($state.$current.data).toBeDefined();
        });

        it('should has current state without data', function () {
            $state.go('nodata');
            $rootScope.$apply();
            expect($state.$current).toBeDefined();
            expect($state.$current.data).not.toBeDefined();
        });

        it('breadcrumbData() should resolve empty breadcrumb with testTitle', function () {
            $state.go('parent');
            $rootScope.$apply();
            var bcData = headerService.breadcrumbData();
            expect(bcData.title).toBe(parentTitle);
            expect(bcData.parents).toEqual([]);
        });

        it('breadcrumbData() should resolve breadcrumb', function () {
            $state.go('parent.test');
            $rootScope.$apply();
            var bcData = headerService.breadcrumbData();
            expect(bcData.title).toBe(testTitle);
            expect(bcData.parents).not.toEqual([]);
            expect(bcData.parents).toContain({
                title: parentTitle,
                name: 'parent'
            });
            expect(bcData.parents.length).toBe(1);
        });

    });
})();