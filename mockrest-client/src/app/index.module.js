'use strict';

import themingConfig from './index.theme.js';
import routerConfig from './index.route.js';

import coreModule from './components/core/core.js';
import layoutModule from './components/layout/layout';
import securityModule from './components/security/security';

import homeModule from './home/home.js';

/* global toastr:false, moment:false */

angular
    .module('mockrest', [
        coreModule.name,
        layoutModule.name,
        securityModule.name,
        homeModule.name
    ])
    .config(themingConfig)
    .config(routerConfig)
    .constant('moment', moment)
    .constant('toastr', toastr);
