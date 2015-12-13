'use strict';

/**
 * @ngdoc function
 * @name Carrot.controller:AccountController
 * @description
 * # AccountController
 *
 * Controller managing the account
 */
angular.module('Carrot')
    .controller('AccountController', function ($scope, $rootScope, $location, $cookieStore, $log, flash, User) {
        $scope.user = {};
        $scope.user.name = $rootScope.user.name;
        $scope.user.role = $rootScope.user.role;
        $scope.updateAccount = function () {
            User.save({id: $scope.user.id}, $scope.user, function () {
                flash.success = "Account updated.";
                $rootScope.user =  $scope.user;
                $cookies.put('user', JSON.stringify($scope.user), {
                    expires: moment().add(1, "hours").toDate()
                });
            }, function (httpResponse) {
                flash.error = "There was an error processing your request.";
            })
        };
    });
