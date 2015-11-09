/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.Beacon
 * @description
 * # Carrot
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('Beacon', function ($resource) {
        return $resource(baseURL + '/client/beacons/:id');
    });
