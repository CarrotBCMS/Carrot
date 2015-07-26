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
        'uiSwitch',
        'ui.bootstrap.datetimepicker',
        'oi.multiselect',
        'angularMoment'
    ])
    .config(function ($routeProvider, $httpProvider, flashProvider, cfpLoadingBarProvider) {
        // General
        flashProvider.errorClassnames.push('alert-danger');
        flashProvider.warnClassnames.push('alert-warning');
        flashProvider.infoClassnames.push('alert-info');
        flashProvider.successClassnames.push('alert-success');
        cfpLoadingBarProvider.includeSpinner = true;
        //cfpLoadingBarProvider.latencyThreshold = 200;
        $httpProvider.interceptors.push(function ($q, $rootScope) {
            return { 'responseError': function (response) {
                if (response.status == 403) {
                    flash.error = "Ups, you are not logged in.";
                    $rootScope.logout();
                    return;
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
                controller: 'AppViewController'
            })
            .when('/apps/new', {
                templateUrl: 'views/apps_edit.html',
                controller: 'AppViewController'
            })
            .when('/beacons', {
                templateUrl: 'views/beacons.html',
                controller: 'BeaconController'
            })
            .when('/beacons/:id', {
                templateUrl: 'views/beacons_edit.html',
                controller: 'BeaconViewController'
            })
            .when('/beacons/new', {
                templateUrl: 'views/beacons_edit.html',
                controller: 'BeaconViewController'
            })
            .when('/events', {
                templateUrl: 'views/events.html',
                controller: 'EventController'
            })
            .when('/events/:id', {
                templateUrl: 'views/events_edit.html',
                controller: 'EventViewController'
            })
            .when('/events/new', {
                templateUrl: 'views/events_edit.html',
                controller: 'EventViewController'
            })

            // General
            .otherwise({
                redirectTo: "/login"
            });
    }).run(function ($rootScope, $http, $location, $cookieStore, $log) {
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
            $cookieStore.remove("user");
            $location.path("/login");
        };

        $rootScope.go = function (path) {
            $location.path(path);
        };

        /** User related **/
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
    }).filter('capitalize', function () {
        return function (input, scope) {
            if (input == null) {
                return;
            }
            
            input = input.toLowerCase();
            return input.substring(0, 1).toUpperCase() + input.substring(1);
        }
    }).directive('dateField', function ($filter) {
        var parseExp = "EEEE, MMMM d yyyy - HH:mm";
        return {
            require: 'ngModel',
            link: function (scope, element, attrs, ngModelController) {
                ngModelController.$parsers.push(function (data) {
                    var date = Date.parse(data, parseExp);
                    ngModelController.$setValidity('date', date != null);
                    return date == null ? undefined : date;
                });
                ngModelController.$formatters.push(function (data) {
                    return $filter('date')(data, parseExp);
                });
            }
        }
    });

var baseURL = "http://localhost:8080";
