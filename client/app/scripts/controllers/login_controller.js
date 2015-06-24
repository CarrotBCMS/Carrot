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
    .controller('LoginController', function ($scope, $http, $cookieStore, $location, $rootScope, LoginService, flash) {
        $scope.login = function () {
            LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function (user) {
                $cookieStore.put('user', user);
                $rootScope.user = user;
                $http.defaults.headers.common['x-auth-token'] = user.token;
                $location.path("/");
            }, function (httpResponse) {
                flash.error = "There was an error processing your request.";
                if (httpResponse.status == 403 || httpResponse.status == 401) {
                    flash.error = "Wrong username or password.";
                }
            });
        };
    });