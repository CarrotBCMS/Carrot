/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:ResetController
 * @description
 * # ResetController
 *
 * Controller resetting password
 */
angular.module('Carrot')
    .controller('ResetController', function ($scope, $rootScope, $routeParams, $location, flash, ActivationService) {
        var email = $routeParams.email;
        var token = $routeParams.token;
        if (token != null && email != null) {
            ActivationService.reset(token, email, function (success, status) {
                $rootScope.$on('$routeChangeSuccess', function () {
                    if (success) {
                        flash.success = "Password reset. Check your emails for a new password.";
                    } else {
                        flash.error = "There was an error resetting your password.";
                    }
                    $rootScope.$$listeners['$routeChangeSuccess'].pop();
                });
            });
        } else {
            $location.path("/login");
        }
    });