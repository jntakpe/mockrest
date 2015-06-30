'use strict';

import config from './index.config.js';
import routerConfig from './index.route.js';
import runConfig from './index.run.js';

import coreModule from './components/core/core.js';
import layoutModule from './components/layout/layout';
import securityModule from './components/security/security';

import homeModule from './home/home.js';


angular.module('mockrest', [
    coreModule.name,
    layoutModule.name,
    securityModule.name,
    homeModule.name
]).config(config)
    .config(routerConfig);
