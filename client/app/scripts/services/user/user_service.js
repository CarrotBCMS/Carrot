/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.User
 * @description
 * # User
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('User', function ($resource) {
        return $resource(baseURL + '/client/users/:id');
    });
