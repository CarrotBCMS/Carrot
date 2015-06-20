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
        return {"delete": function (object, objectClass, callback) {
            $modal.open({
                templateUrl: 'views/delete.html',
                backdrop: true,
                windowClass: 'modal',
                controller: function ($scope, $modalInstance, $route, $location, $injector) {
                    $scope.submit = function () {
                        var objectService = $injector.get(objectClass); // Retrieve service
                        objectService.delete({"id": object.id}, function () {
                            callback(object);
                            $modalInstance.dismiss('cancel');
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