/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.Event
 * @description
 * # Carrot
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('Event', function ($resource) {
        return $resource(baseURL + '/client/events/:id');
    });
