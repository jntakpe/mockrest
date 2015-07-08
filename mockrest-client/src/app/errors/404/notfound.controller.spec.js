(function () {
    'use strict';

    describe('Controller : NotFoundCtrl as notFound => ', function () {

        var scope, ctrl;

        beforeEach(function () {
            module('mockrest');

            inject(function ($controller) {
                scope = {};
                ctrl = $controller('NotFoundCtrl as notFound', {
                    $scope: scope
                });
            });
        });

        it('should exist', function () {
            expect(!!ctrl).toBe(true);
        });

    });
})();
