(function () {
    'use strict';

    describe('Controller : HeaderCtrl as header => ', function () {

        var ctrl, anonCtlr, usernameVal = 'testUser';

        beforeEach(function () {
            module('mockrest');

            inject(function ($controller, _$rootScope_, _$timeout_) {
                ctrl = $controller('HeaderCtrl as header', {
                    $scope: _$rootScope_.$new(),
                    $timeout: _$timeout_,
                    authorize: {login: usernameVal}
                });
                anonCtlr = $controller('HeaderCtrl as header', {
                    $scope: _$rootScope_.$new(),
                    $timeout: _$timeout_,
                    authorize: {}
                });
            });
        });

        it('should exist', function () {
            expect(!!ctrl).toBe(true);
        });

        it('should not attach username', function () {
            expect(anonCtlr.username).not.toBeDefined();
        });

        it('should attach username', function () {
            expect(ctrl.username).toBeDefined();
        });

        it('should has username value equals to testUser', function () {
            expect(ctrl.username).toBe(usernameVal);
        });
    });
})();