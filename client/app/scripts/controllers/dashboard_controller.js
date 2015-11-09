/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

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
    .controller('DashboardController', function ($scope, $log, flash, ngTableParams, AnalyticsService) {
        $scope.countsResolved = false;
        AnalyticsService.count().then(function (data) {
            $scope.beaconCount = data.beacons;
            $scope.appCount = data.apps;
            $scope.eventCount = data.events;
            $scope.countsResolved = true;
        });

        $scope.sections = ["Apps", "Beacons", "Events"];
        $scope.section = $scope.sections[0];

        $scope.ranges = ["Today", "Last 7 days", "Last 30 days"];
        $scope.range = $scope.ranges[0];

        $scope.data = [];

        $scope.tableParams = new ngTableParams({
            page: 1,
            count: 10
        }, {
            total: $scope.data.length,
            getData: function ($defer, params) {
                var pageData = $scope.data.slice((params.page() - 1) * params.count(), params.page() * params.count());
                params.total($scope.data.length);

                // Fix empty page after deletion
                if (pageData.length == 0 && $scope.data.length > 0) {
                    pageData = $scope.data.slice((params.page() - 2) * params.count(), params.page() * params.count());
                }

                $defer.resolve(pageData);
            }
        });

        $scope.updateData = function () {
            var url = $scope.section.toLowerCase();
            var from = moment().startOf("day");
            var to = moment().endOf("day");

            // This Week
            if ($scope.range == $scope.ranges[1]) {
                from = moment().startOf("day").subtract(7, "days");
            }

            // This Month
            if ($scope.range == $scope.ranges[2]) {
                from = moment().startOf("day").subtract(30, "days");
            }

            AnalyticsService.analytics(url, from, to).then(function (result) {
                $scope.data = result;
                $log.debug(result);
                $scope.tableParams.reload();
            }, function (response) {
                flash.error = "There was an error processing your request.";
            });
        };

        $scope.updateData();
    });
