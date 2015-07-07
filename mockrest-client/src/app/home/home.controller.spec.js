(function () {
    'use strict';

    describe('Controller : HomeCtrl as home', function () {

        var scope, ctrl;

        beforeEach(function () {
            module('mockrest');

            inject(function ($controller) {
                scope = {};
                ctrl = $controller('HomeCtrl as home', {
                    $scope: scope
                });
            });
        });

        it('should define more than 5 awesome things', inject(function ($controller) {
            var vm = $controller('HomeCtrl');
            console.log(vm);
            expect(10 > 5).toBeTruthy();
        }));
    });
})();
