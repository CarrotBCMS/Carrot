'use strict';

/**
 * @ngdoc function
 * @name Carrot.directive:dateField
 * @description
 * # dateField
 *
 * Directive providing a valid date field.
 */
angular.module('Carrot')
    .directive('dateField', function ($filter) {
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