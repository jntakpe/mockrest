(function () {
    'use strict';

    /* global toastr:false, moment:false */

    angular.module('fra')
        .constant('moment', moment)
        .constant('toastr', toastr);
})();