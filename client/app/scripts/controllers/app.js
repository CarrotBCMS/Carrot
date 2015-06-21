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
    .controller('AppController', function ($scope, App, EntityService) {
        EntityService.list($scope, App);
    });

/**
 * @ngdoc function
 * @name Carrot.controller:AppViewController
 * @description
 * # AppViewController
 *
 * Controller showing a single app
 */
angular.module('Carrot')
    .controller('AppViewController', function ($scope, $route, $location, $routeParams, flash, App) {
        var basePath = "apps";
        var baseName = "App";
        $scope.isNew = $routeParams.id == 'new';
        if ($scope.isNew) {
            $scope.app = new App();
        } else {
            $scope.app = App.get({id: $routeParams.id}, function () {
                // Do nothing yet
            }, function (error) {
                $location.path("/").replace(); // Redirect to base path if there was an error
            });
        }

        $scope.deleteEntry = App.delete;
        $scope.submit = function () {
            App.save($scope.app, function (object) {
                $scope.app = object;
                flash.success = "Entry saved.";
            }, function(httpResponse) {
                flash.error = "There was an error processing your request.";
            });
        };
    });
