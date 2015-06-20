'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:AppController
 * @description
 * # AppController
 *
 * Controller showing apps
 */
angular.module('Carrot')
    .controller('AppController', function ($scope, $log, ngTableParams, App, Entity) {
        var data = App.query(function(data) {
            $scope.tableParams = new ngTableParams({
                page: 1,
                count: 10
            }, {
                total: data.length,
                getData: function ($defer, params) {
                    var pageData = data.slice((params.page() - 1) * params.count(), params.page() * params.count());
                    $log.debug(pageData);
                    $defer.resolve(pageData);
                }
            })
        });

        $scope.delete = Entity.delete;

        $scope.edit = function(item) {
            $log.debug("Edit item " + item);
        };
    });
