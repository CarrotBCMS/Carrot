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
        AnalyticsService.count().then(function(data) {
            $scope.beaconCount = data.beacons;
            $scope.appCount = data.apps;
            $scope.eventCount = data.events;
        });

        AnalyticsService.logs().then(function(data) {
          //  $scope.logs = data;
        }, function(response) {
            flash.error = "There was an error processing your request.";
        });

        $scope.sections = ["Apps", "Beacons", "Events"];
        $scope.section = $scope.sections[0];

        $scope.ranges = ["Today", "This Week", "This Month"];
        $scope.range = $scope.ranges[0];

        var data = [];

        $scope.tableParams = new ngTableParams({
            page: 1,
            count: 10
        }, {
            total: data.length,
            getData: function ($defer, params) {
                var pageData = data.slice((params.page() - 1) * params.count(), params.page() * params.count());
                params.total(data.length);

                // Fix empty page after deletion
                if (pageData.length == 0 && data.length > 0) {
                    pageData = data.slice((params.page() - 2) * params.count(), params.page() * params.count());
                }

                $defer.resolve(pageData);
            }
        });

        $scope.updateData = function() {
            var url = $scope.section.toLowerCase();
            var from = moment().startOf("day");
            var to = moment().endOf("day");

            // This Week
            if ($scope.range == $scope.ranges[1]) {
                from = moment().startOf("week");
                to = moment().endOf("week");
            }

            // This Month
            if ($scope.range == $scope.ranges[2]) {
                from = moment().startOf("month");
                to = moment().endOf("month");
            }

            AnalyticsService.analytics(url, from, to).then(function(hash) {
                var item;
                data = [];
                for (var i = 0, keys = Object.keys(hash), ii = keys.length; i < ii; i++) {
                    item = {};
                    item.name = keys[i];
                    item.count = hash[keys[i]];
                    data.push(item);
                }

                $scope.tableParams.reload();
            }, function(response) {
                flash.error = "There was an error processing your request.";
            });
        };

        $scope.updateData();
    });
