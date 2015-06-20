'use strict';

/**
 * @ngdoc service
 * @name Carrot.Entity
 * @description
 * # Entity
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('EntityService', function ($modal, ngTableParams, $log) {
        var delFunction = function (object, factory, callback) {
            $modal.open({
                templateUrl: 'views/delete.html',
                backdrop: true,
                windowClass: 'modal',
                controller: function ($scope, $modalInstance, $route, $location, $injector) {
                    $scope.submit = function () {
                        factory.delete({"id": object.id}, function () {
                            callback(object);
                            $modalInstance.dismiss('cancel');
                        });
                    };

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            });
        };

        var listFunction = function (scope, factory) {
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
                    }
                });
            });

            scope.delete = function (item) {
                delFunction(item, factory, function (object) {
                    var index = scope.data.indexOf(item);
                    if (index > -1) {
                        scope.data.splice(index, 1);
                    }
                    scope.tableParams.reload()
                });
            };

            scope.edit = function (item) {
                $log.debug("Edit item " + item);
            };

            scope.add = function () {
                $log.debug("Add item");
            };
        };

        return {
            "delete": delFunction,
            "list": listFunction
        }
    });