'use strict';

var eventTypes = ['On Enter', 'On Exit', 'On Exit / On Exit'];

/**
 * @ngdoc function
 * @name Carrot.controller:EventController
 * @description
 * # EventController
 *
 * Controller showing beacons
 */
angular.module('Carrot')
    .controller('EventController', function ($scope, Event, EntityService) {
        EntityService.list($scope, Event);

        $scope.typeToString = function (objectType) {
            switch(objectType) {
                case 0:
                    return eventTypes[0];
                    break;
                case 1:
                    return eventTypes[1];
                    break;
                default:
                    return eventTypes[2];
            }
        };
    });


/**
 * @ngdoc function
 * @name Carrot.controller:EventViewController
 * @description
 * # EventViewController
 *
 * Controller showing a single event
 */
angular.module('Carrot')
    .controller('EventViewController', function ($scope, $location, Event, EntityService, $log) {
        EntityService.edit($scope, Event, "Event");
        $scope.delete = function () {
            EntityService.delete($scope.object, Event, function () {
                $location.path("/events").replace();
            });
        };

        // Assign defaults
        if ($scope.object.objectType === undefined) {
            $scope.object.objectType = "text";
        }

        if ($scope.object.eventType == undefined) {
            $scope.object.eventType = 0;
        }

        if ($scope.object.active === undefined) {
            $scope.object.active = 1;
        }

        $scope.parseInt = function(number) {
            return parseInt(number, 10);
        };
        $scope.eventTypes = eventTypes;
    });