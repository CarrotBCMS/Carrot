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
    .controller('EventController', function ($scope, $log, Event) {
        $scope.events = Event.query(function(data) {
            $log.debug(JSON.stringify(data));
        });;
    });
