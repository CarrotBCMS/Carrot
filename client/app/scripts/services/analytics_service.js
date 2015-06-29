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
        var countFunction = function() {
            var promise = $http.get(baseURL + "/client/analytics/count").then(function (response) {
                $log.debug(response.data);
                return response.data;
            });
            return promise;
        };

        return {
            "count": countFunction
        }
    });
