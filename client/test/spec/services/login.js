/*
 * Carrot - beacon management
 * Copyright (C) 2015 Heiko Dreyer
 */

'use strict';

describe('Service: LoginService', function () {
    // load the service's module
    beforeEach(module('Carrot'));

    // instantiate service
    var login;
    beforeEach(inject(function (_LoginService_) {
        login = _LoginService_;
    }));

    it('should get the category resource promise', function () {
        expect(login).toBeDefined();
        expect(login.authenticate()).toBeDefined();
    });
});
