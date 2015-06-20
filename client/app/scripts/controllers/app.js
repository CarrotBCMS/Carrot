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
    .controller('AppController', function ($scope, App) {
        $scope.apps = App.query(function(data) {
            $log.debug(JSON.stringify(data));
        });;
    });
