/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.AnalyticsService
 * @description
 * # Carrot
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('AnalyticsService', function ($http, $log) {
        var countFunction = function () {
            var promise = $http.get(baseURL + "/client/analytics/count").then(function (response) {
                return response.data;
            });
            return promise;
        };

        var logsFunction = function () {
            var promise = $http.get(baseURL + "/client/analytics/logs").then(function (response) {
                $log.debug(response.data);
                $log.debug("bla");
                return response.data;
            });
            return promise;
        };

        var analyticsFunction = function (url, from, to) {
            var promise = $http.get(baseURL + "/client/analytics/" + url, {
                params: {
                    from: from.format("YYYY-MM-DDTHH:mm:ss.SSSZ"),
                    to: to.format("YYYY-MM-DDTHH:mm:ss.SSSZ")
                }
            }).then(function (response) {
                $log.debug(response.data);
                return response.data;
            });
            return promise;
        };

        return {
            "count": countFunction,
            "analytics": analyticsFunction,
            "logs": logsFunction
        }
    });
