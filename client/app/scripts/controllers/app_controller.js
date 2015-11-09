/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:AppController
 * @description
 * # AppController
 *
 * Controller showing apps
 */
angular.module('Carrot')
    .controller('AppController', function ($scope, App, EntityService) {
        EntityService.list($scope, App, "App");
    });

/**
 * @ngdoc function
 * @name Carrot.controller:AppDetailController
 * @description
 * # AppDetailController
 *
 * Controller showing a single app
 */
angular.module('Carrot')
    .controller('AppDetailController', function ($scope, $location, App, EntityService) {
        EntityService.edit($scope, App, "App");
        $scope.delete = function () {
            EntityService.delete($scope.object, App, function () {
                $location.path("/apps").replace();
            });
        }
    });
