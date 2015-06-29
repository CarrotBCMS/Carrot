'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:DashboardController
 * @description
 * # DashboardController
 *
 * Controller showing the dashboard
 */
angular.module('Carrot')
    .controller('DashboardController', function ($scope, flash, AnalyticsService) {
        AnalyticsService.count().then(function(data) {
            $scope.beaconCount = data.beacons;
            $scope.appCount = data.apps;
            $scope.eventCount = data.events;
        });

        AnalyticsService.logs().then(function(data) {
            $scope.logs = data;
        }, function(response) {
            flash.error = "There was an error processing your request.";
        });
    });
