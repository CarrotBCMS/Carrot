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
        EntityService.list($scope, Beacon);
    });
