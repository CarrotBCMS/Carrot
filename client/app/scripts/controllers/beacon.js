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
    .controller('BeaconController', function ($scope, $log, Beacon) {
        $scope.beacons = Beacon.query(function(data) {
            $log.debug(JSON.stringify(data));
        });;
    });
