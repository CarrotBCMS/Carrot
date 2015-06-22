'use strict';

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

        $scope.typeToString = function (eventType) {
            switch(eventType) {
                case 0:
                    return "On Enter";
                    break;
                case 1:
                    return "On Exit";
                    break;
                default:
                    return "On Enter / On Exit";
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
    .controller('EventViewController', function ($scope, $location, Event, EntityService) {
        EntityService.edit($scope, Event, "Event");
        $scope.delete = function () {
            EntityService.delete($scope.object, Event, function () {
                $location.path("/events").replace();
            });
        }
    });