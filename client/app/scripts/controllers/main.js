'use strict';

/**
 * @ngdoc function
 * @name carrotApp.controller:MainCtrl
 * @description
 * # MainCtrl
 * Controller of the carrotApp
 */
angular.module('carrotApp')
  .controller('MainCtrl', function ($scope) {
    $scope.awesomeThings = [
      'HTML5 Boilerplate',
      'AngularJS',
      'Karma'
    ];
  });
