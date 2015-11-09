/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:BeaconController
 * @description
 * # BeaconController
 *
 * Controller showing beacons
 */
angular.module('Carrot')
    .controller('BeaconController', function ($scope, Beacon, EntityService) {
        EntityService.list($scope, Beacon, "Beacon");
    });

/**
 * @ngdoc function
 * @name Carrot.controller:BeaconDetailController
 * @description
 * # BeaconDetailController
 *
 * Controller showing a single beacon
 */
angular.module('Carrot')
    .controller('BeaconDetailController', function ($scope, $location, Beacon, EntityService) {
        EntityService.edit($scope, Beacon, "Beacon");
        $scope.delete = function () {
            EntityService.delete($scope.object, Beacon, function () {
                $location.path("/beacons").replace();
            });
        }
    });