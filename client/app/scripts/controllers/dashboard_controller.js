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
    .controller('DashboardController', function ($scope, AnalyticsService) {
        AnalyticsService.count().then(function(data) {
            $scope.beaconCount = data.beacons;
            $scope.appCount = data.apps;
            $scope.eventCount = data.events;
        });

    });
