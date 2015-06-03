'use strict';

/**
 * @ngdoc function
 * @name carrotApp.controller:AboutCtrl
 * @description
 * # AboutCtrl
 * Controller of the carrotApp
 */
angular.module('carrotApp')
  .controller('AboutCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
