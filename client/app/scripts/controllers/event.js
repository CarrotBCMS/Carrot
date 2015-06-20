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
