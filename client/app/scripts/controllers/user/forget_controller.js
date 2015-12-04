/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:ForgetController
 * @description
 * # ForgetController
 *
 * Controller retrieving forgotten passwords
 */
angular.module('Carrot')
    .controller('ForgetController', function ($scope, $rootScope, $routeParams, $location, flash, ActivationService) {
        $scope.forget = function () {
            ActivationService.forget($scope.email, function (success) {
                $rootScope.$on('$routeChangeSuccess', function () {
                    if (success) {
                        flash.success = "Check your emails to reset your password.";
                    } else {
                        flash.error = "There was an error while trying to generate your reset link.";
                    }
                    $rootScope.$$listeners['$routeChangeSuccess'].pop();
                });
            });
        }
    });