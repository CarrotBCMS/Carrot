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

/**
 * @ngdoc function
 * @name Carrot.controller:BeaconViewController
 * @description
 * # BeaconViewController
 *
 * Controller showing a single beacon
 */
angular.module('Carrot')
    .controller('BeaconViewController', function ($scope, $location, Beacon, EntityService) {
        EntityService.edit($scope, Beacon, "beacons", "Beacon");
        $scope.delete = function () {
            EntityService.delete($scope.object, Beacon, function () {
                $location.path("/beacons").replace();
            });
        }
    });