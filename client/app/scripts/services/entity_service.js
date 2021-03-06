/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.Entity
 * @description
 * # Entity
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('EntityService', function ($modal, ngTableParams, flash, $location, $routeParams, $timeout) {
        var delFunction = function (object, factory, callback) {
            $modal.open({
                templateUrl: 'views/fragments/delete.html',
                backdrop: true,
                windowClass: 'modal',
                controller: function ($scope, $modalInstance, $route, $location, $injector) {
                    $scope.submit = function () {
                        factory.delete({"id": object.id}, function () {
                            if (callback) {
                                callback(object);
                            }
                            $modalInstance.dismiss('cancel');
                        });
                    };

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            });
        };

        var listFunction = function (scope, factory, categoryName) {
            scope.resolved = false;
            scope.data = factory.query(function (data) {
                scope.tableParams = new ngTableParams({
                    page: 1,
                    count: 10
                }, {
                    total: data.length,
                    getData: function ($defer, params) {
                        var pageData = data.slice((params.page() - 1) * params.count(), params.page() * params.count());
                        params.total(data.length);

                        // Fix empty page after deletion
                        if (pageData.length == 0 && data.length > 0) {
                            pageData = data.slice((params.page() - 2) * params.count(), params.page() * params.count());
                        }

                        $defer.resolve(pageData);
                        $timeout(function() {
                            scope.resolved = true;
                        })

                    }
                });
            });

            scope.delete = function (item) {
                delFunction(item, factory, function (object) {
                    var index = scope.data.indexOf(item);
                    if (index > -1) {
                        scope.data.splice(index, 1);
                    }
                    flash.success = categoryName + " deleted.";
                    scope.tableParams.reload();
                });
            };
        };

        var editFunction = function(scope, factory, categoryName) {
            scope.isNew = $routeParams.id == 'new';
            if (scope.isNew) {
                scope.object = {};
            } else {
                scope.object = factory.get({id: $routeParams.id}, function () {
                    // Do nothing yet
                }, function (error) {
                    $location.path("/").replace(); // Redirect to base path if there was an error
                });
            }

            scope.submit = function () {
                if (scope.object.scheduledStartDate != undefined) {
                    scope.object.scheduledStartDate = new Date(scope.object.scheduledStartDate);
                }
                factory.save(scope.object, function (object) {
                    scope.isNew = false;
                    scope.object = object;
                    flash.success = categoryName + " saved.";
                }, function(response) {
                    if (response.status == 422) {
                        flash.error = categoryName + " already exists.";
                    } else {
                        flash.error = "There was an error processing your request.";
                    }
                });
            };
        };

        return {
            "delete": delFunction,
            "list": listFunction,
            "edit": editFunction
        }
    });