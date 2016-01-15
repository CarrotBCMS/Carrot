/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

describe('Service: EntityService', function () {
    // load the service's module
    beforeEach(module('Carrot'));

    // instantiate service
    var entity;
    beforeEach(inject(function (_EntityService_) {
        entity = _EntityService_;
    }));

    it('should get category methods', function () {
        expect(entity).toBeDefined();
        expect(entity.list).toBeDefined();
        expect(entity.edit).toBeDefined();
        expect(entity.delete).toBeDefined();
    });
});
