(function () {
    'use strict';

    /* global toastr:false, moment:false */

    angular.module('mockrest')
        .constant('moment', moment)
        .constant('toastr', toastr);
})();