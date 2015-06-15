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
    .controller('LoginController', function ($scope, $http, $localStorage, $location, LoginService) {
        $scope.login = function () {
            LoginService.authenticate($.param({username: $scope.username, password: $scope.password}), function (user) {
                $localStorage.user = user;
                $http.defaults.headers.common['x-auth-token'] = user.token;
                $location.path("/");
            }, function (httpResponse) {
                // Handle errors later
            });
        };
    });