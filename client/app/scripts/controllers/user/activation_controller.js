/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:ActivationController
 * @description
 * # ActivationController
 *
 * Controller activating users
 */
angular.module('Carrot')
    .controller('ActivationController', function ($scope, $rootScope, $routeParams, $location, flash, ActivationService) {
        var token = $routeParams.token;
        var email = $routeParams.email;
        if (token != null && email != null) {
            ActivationService.activate(token, email, function (success) {
                $rootScope.$on('$routeChangeSuccess', function () {
                    if (success) {
                        flash.success = "Account activated. Sign in with your email and password.";
                    } else {
                        flash.error = "There was an error activating your account. Please try again.";
                    }
                    $rootScope.$$listeners['$routeChangeSuccess'].pop();
                });
            });
        } else {
            $location.path("/login");
        }
    });