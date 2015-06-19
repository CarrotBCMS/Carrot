'use strict';

/**
 * @ngdoc overview
 * @name Carrot
 * @description
 * # Carrot
 *
 * Main module of the application.
 */
angular
    .module('Carrot', [
        'ngAnimate',
        'ngCookies',
        'ngResource',
        'ngRoute',
        'ngSanitize',
        'ngTouch',
        'angular-flash.service',
        'angular-flash.flash-alert-directive'
    ])
    .config(function ($routeProvider, flashProvider) {
        // General
        flashProvider.errorClassnames.push('alert-danger');
        flashProvider.warnClassnames.push('alert-warning');
        flashProvider.infoClassnames.push('alert-info');
        flashProvider.successClassnames.push('alert-success');

        $routeProvider
            // Login
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController'
            })
            .otherwise({
                redirectTo: '/'
            })

            // Content
            .when('/', {
                templateUrl: 'views/dashboard.html',
                controller: 'DashboardController'
            })

            // General
            .otherwise({
                redirectTo: "/login"
            });
    }).run(function ($rootScope, $http, $location, $cookieStore) {
        $rootScope.logout = function () {
            delete $http.defaults.headers.common["x-auth-token"];
            delete $rootScope.user;
            $cookieStore.remove("user");
            $location.path("/login");
        };

        /* Try getting valid user session cookie or go to login page */
        var originalPath = $location.path();
        var user = $cookieStore.get("user");

        if (user !== undefined) {
            $rootScope.user = user;
            $http.defaults.headers.common["x-auth-token"] = user.token;
            $location.path(originalPath);
        } else {
            $location.path("/login");
        }
    });

var baseURL = "http://localhost:8080";
