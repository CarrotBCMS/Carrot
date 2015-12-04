/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

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
        'ngTable',
        'angular-flash.service',
        'angular-flash.flash-alert-directive',
        'angular-loading-bar',
        'ui.bootstrap',
        'angular.validators',
        'validation.match',
        'uiSwitch',
        'ui.bootstrap.datetimepicker',
        'oi.multiselect',
        'angularMoment'
    ])
    .config(function ($routeProvider, $httpProvider, $locationProvider, flashProvider, cfpLoadingBarProvider) {
        // Enable html5 mode
        //$locationProvider.html5Mode(true);

        // General
        flashProvider.errorClassnames.push('alert-danger');
        flashProvider.warnClassnames.push('alert-warning');
        flashProvider.infoClassnames.push('alert-info');
        flashProvider.successClassnames.push('alert-success');
        cfpLoadingBarProvider.includeSpinner = true;
        //cfpLoadingBarProvider.latencyThreshold = 200;

        $httpProvider.interceptors.push(function ($q, $rootScope, flash) {
            return {
                'responseError': function (response) {
                    if (response.status == 403) {
                        flash.error = "Ups, you are not logged in.";
                        $rootScope.logout();
                    }
                    return $q.reject(response);
                }
            };
        });

        $routeProvider
            // Login
            .when('/login', {
                templateUrl: 'views/login.html',
                controller: 'LoginController'
            })
            // Content
            .when('/', {
                templateUrl: 'views/dashboard.html',
                controller: 'DashboardController'
            })
            .when('/apps', {
                templateUrl: 'views/apps.html',
                controller: 'AppController'
            })
            .when('/apps/:id', {
                templateUrl: 'views/apps_edit.html',
                controller: 'AppDetailController'
            })
            .when('/apps/new', {
                templateUrl: 'views/apps_edit.html',
                controller: 'AppDetailController'
            })
            .when('/beacons', {
                templateUrl: 'views/beacons.html',
                controller: 'BeaconController'
            })
            .when('/beacons/:id', {
                templateUrl: 'views/beacons_edit.html',
                controller: 'BeaconDetailController'
            })
            .when('/beacons/new', {
                templateUrl: 'views/beacons_edit.html',
                controller: 'BeaconDetailController'
            })
            .when('/events', {
                templateUrl: 'views/events.html',
                controller: 'EventController'
            })
            .when('/events/:id', {
                templateUrl: 'views/events_edit.html',
                controller: 'EventDetailController'
            })
            .when('/events/new', {
                templateUrl: 'views/events_edit.html',
                controller: 'EventDetailController'
            })

            // User
            .when('/activate', {
                templateUrl: 'views/user/activation.html',
                controller: 'ActivationController'
            })

            .when('/register', {
                templateUrl: 'views/user/register.html',
                controller: 'RegisterController'
            })

            .when('/reset', {
                templateUrl: 'views/user/reset.html',
                controller: 'ResetController'
            })

            .when('/forgot', {
                templateUrl: 'views/user/forget.html',
                controller: 'ForgetController'
            })

            // General
            .otherwise({
                redirectTo: "/login"
            });
    }).run(function ($rootScope, $http, $location, $cookies, $log) {
    /* Route changes */
    $rootScope.$on('$routeChangeStart', function (ev, next, curr) {
        if (next.$$route) {
            var user = $rootScope.user;
            if (user && next.$$route.originalPath == "/login") {
                $location.path('/')
            }
        }
    });

    /** Global functions **/
    $rootScope.isActive = function (viewLocation) {
        if (viewLocation == "/") {
            return viewLocation === $location.path();
        }

        return $location.path().indexOf(viewLocation) > -1;
    };

    $rootScope.logout = function () {
        delete $http.defaults.headers.common["x-auth-token"];
        delete $rootScope.user;
        $cookies.remove("user");
        $location.path("/login");
    };

    $rootScope.go = function (path) {
        $location.path(path);
    };

    /** User related **/
    /* Try getting valid user session cookie or go to login page */
    var allowedPaths = ["/activate", "/reset", "/forgot"];
    var originalPath = $location.path();
    var user = $cookies.get("user");

    if (user !== undefined) {
        user = JSON.parse(user);
        $rootScope.user = user;
        $http.defaults.headers.common["x-auth-token"] = user.token;
        $location.path(originalPath);
    } else {
        if (allowedPaths.indexOf(originalPath) != -1) {
            $location.path(originalPath);
        } else {
            $location.path("/login");
        }
    }
});

var baseURL = "http://localhost:8080";
