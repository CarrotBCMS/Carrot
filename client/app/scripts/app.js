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
        'ngStorage'
    ])
    .config(function ($routeProvider) {
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
    }).run(function ($rootScope, $http, $location, $localStorage) {
        $rootScope.$storage = $localStorage;
        $rootScope.logout = function () {
            delete $rootScope.user;
            delete $http.defaults.headers.common[tokenHeader];
            $cookieStore.remove("user");
            $location.path("/login");
        };

        /* Try getting valid user session cookie or go to login page */
        var originalPath = $location.path();
        var user = $rootScope.$storage.user;

        if (user !== undefined) {
            $rootScope.user = user;
            $http.defaults.headers.common[tokenHeader] = user.token;
            $location.path(originalPath);
        } else {
            $location.path("/login");
        }
    });

var baseURL = "http://localhost:8080";
