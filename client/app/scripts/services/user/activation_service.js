/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.ActivationService
 * @description
 * # ActivationService
 * Factory in Carrot.
 */
angular.module('papayaApp')
    .factory('ActivationService', function ($http, $location, $log) {
        var callbackReset = function () {
            // Clear params
            delete $location.$$search.token;
            delete $location.$$search.email;
            $location.$$compose();

            // Redirect to login
            $location.path("/login");
        };

        return {
            activate: function (token, email, callback) {
                // Activate account
                $http.get(baseURL + '/rest/activate?email=' + email + '&token=' + token).
                    success(function (data, status, headers, config) {
                        $log.info("Successfully activated.");
                        callbackReset();
                        callback(true);
                    }).
                    error(function (data, status, headers, config) {
                        $log.info("Error. Not activated.");
                        callbackReset();
                        callback(false);
                    });
            },
            forget: function (email, callback) {
                $http.get(baseURL + '/rest/forgot?email=' + email).
                    success(function (data, status, headers, config) {
                        $log.info("Successfully resetted password.");
                        callbackReset();
                        callback(true);
                    }).
                    error(function (data, status, headers, config) {
                        $log.info("Error. Password not resetted.");
                        callbackReset();
                        callback(false);
                    });
            },
            reset: function (token, email, callback) {
                $http.get(baseURL + '/rest/reset?email=' + email + '&token=' + token).
                    success(function (data, status, headers, config) {
                        $log.info("Successfully resetted password.");
                        callbackReset();
                        callback(true);
                    }).
                    error(function (data, status, headers, config) {
                        $log.info("Error. Password not resetted.");
                        callbackReset();
                        callback(false);
                    });
            }
        }
    });
