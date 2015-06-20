'use strict';

/**
 * @ngdoc service
 * @name Carrot.Entity
 * @description
 * # Entity
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('Entity', function ($modal) {
        return {"delete": function (object, objectClass, redirect) {
            $modal.open({
                templateUrl: 'views/delete.html',
                backdrop: true,
                windowClass: 'modal',
                controller: function ($scope, $modalInstance, $log, $route, $location, $injector) {
                    $scope.submit = function () {
                        $log.debug('Deleting object.');
                        $log.debug(object);
                        var objectService = $injector.get(objectClass); // Retrieve service
                        $log.debug(objectClass);
                        objectService.delete({"id": object.id}, function () {
                            $modalInstance.dismiss('cancel');
                            if (redirect) {
                                $location.path("/").replace();
                            } else {
                                $route.reload(); // Reload data and route
                            }
                        });
                    };

                    $scope.cancel = function () {
                        $modalInstance.dismiss('cancel');
                    };
                }
            });
        }
        }
    });