/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:RegisterController
 * @description
 * # RegisterController
 *
 * Controller registering users
 */
angular.module('Carrot')
    .controller('RegisterController', function ($scope, $rootScope, $location, flash, User) {
        $scope.registerUser = function() {
            $scope.user = {};
            User.save($scope.user, function () {
                $location.path("/login");
                $rootScope.$on('$routeChangeSuccess', function () {
                    flash.success = "Account created. Check your emails to confirm the registration.";
                    $rootScope.$$listeners['$routeChangeSuccess'].pop();
                });
            }, function (httpResponse) {
                flash.error = "There was an error processing your request.";
                if (httpResponse.status == 422) {
                    flash.error = "Email address already registered.";
                }
            })
        }
    });

/**
 * @ngdoc function
 * @name Carrot.controller:RegisterForgotController
 * @description
 * # RegisterForgotController
 *
 * Controller registering users
 */
angular.module('Carrot')
    .controller('RegisterForgotController', function ($scope) {
    });