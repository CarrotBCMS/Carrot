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
    .controller('DashboardController', function ($scope, $log, flash, AnalyticsService) {
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

        $scope.sections = ["Apps", "Beacons", "Events"];
        $scope.section = $scope.sections[0];

        $scope.ranges = ["Yesterday", "Last Week", "Last Month"];
        $scope.range = $scope.ranges[0];

        $scope.updateData = function() {

        };
    });
