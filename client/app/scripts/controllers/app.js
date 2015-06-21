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
    .controller('AppViewController', function ($scope, $location, App, EntityService) {
        EntityService.edit($scope, App, "apps", "App");
        $scope.delete = function () {
            EntityService.delete($scope.object, App, function () {
                $location.path("/apps").replace();
            });
        }
    });
