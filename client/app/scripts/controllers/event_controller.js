/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

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
        EntityService.list($scope, Event, "Event");

        $scope.typeToString = function (objectType) {
            switch (objectType) {
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
 * @name Carrot.controller:EventDetailController
 * @description
 * # EventDetailController
 *
 * Controller showing a single event
 */
angular.module('Carrot')
    .controller('EventDetailController', function ($scope, $location, Event, Beacon, App, EntityService, $timeout) {
        EntityService.edit($scope, Event, "Event");

        // Multi select settings
        $scope.beacons = Beacon.query();
        $scope.apps = App.query();

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

        if ($scope.object.beacons === undefined) {
            $scope.object.beacons = [];
        }

        if ($scope.object.apps === undefined) {
            $scope.object.apps = [];
        }

        $scope.parseInt = function (number) {
            return parseInt(number, 10);
        };

        $scope.eventTypes = eventTypes;
        $scope.datesAreValid = true;
        $scope.validateDates = function () {
            var startDate = $scope.object.scheduledStartDate;
            var endDate = $scope.object.scheduledEndDate;

            if (startDate == null || endDate == null) {
                $scope.datesAreValid = true;
                return;
            }

            $scope.datesAreValid = startDate.getTime() < endDate.getTime();

            if (!$scope.datesAreValid) {
                $timeout(function(){
                    $scope.datesAreValid = true;
                }, 4000);
                $scope.object.scheduledEndDate = null;
                $scope.object.scheduledStartDate = null;
            }
        };


        $scope.delete = function () {
            EntityService.delete($scope.object, Event, function () {
                $location.path("/events").replace();
            });
        };
    });