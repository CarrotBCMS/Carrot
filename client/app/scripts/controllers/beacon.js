'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:BeaconController
 * @description
 * # BeaconController
 *
 * Controller showing beacons
 */
angular.module('Carrot')
    .controller('BeaconController', function ($scope, $log, ngTableParams, Beacon, Entity) {
        $scope.data = Beacon.query(function (data) {
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
        });

        $scope.delete = function (item) {
            Entity.delete(item, "Beacon", function (object) {
                var index = $scope.data.indexOf(item);
                if (index > -1) {
                    $scope.data.splice(index, 1);
                }
                $scope.tableParams.reload()
            });
        };

        $scope.edit = function (item) {
            $log.debug("Edit item " + item);
        };

        $scope.add = function () {
            $log.debug("Add item");
        };
    });
