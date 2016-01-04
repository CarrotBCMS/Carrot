/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:LoginController
 * @description
 * # LoginController
 *
 * Controller to authorize user(s)
 */
angular.module('Carrot')
    .controller('LoginController', function ($scope, $http, $cookies, $location, $rootScope, LoginService, flash,$log) {
        $scope.register = function() {
            $location.path("/register");
        };

        $scope.login = function () {
            LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function (user) {
                $cookies.put('user', JSON.stringify(user), {
                    expires: moment().add(1, "hours").toDate()
                });
                $rootScope.user = user;
                $http.defaults.headers.common['x-auth-token'] = user.token;
                $location.path("/");
            }, function (httpResponse) {
                flash.error = "There was an error processing your request.";
                if (httpResponse.status == 403 || httpResponse.status == 401) {
                    flash.error = "Your email and password combination has not been found.";
                }
            });
        };
    });