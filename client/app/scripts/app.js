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
        'ngTouch'
    ])
    .config(function ($routeProvider) {
        $routeProvider
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
    });
