/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

/**
 * @ngdoc service
 * @name Carrot.LoginService
 * @description
 * # LoginService
 * Factory in Carrot.
 */
angular.module('Carrot')
    .factory('LoginService', function ($resource) {
        return $resource(baseURL + '/client/authenticate', {},
            {
                authenticate: {
                    method: 'POST',
                    headers: {'Content-Type': 'application/x-www-form-urlencoded'}
                }
            }
        );
    });
