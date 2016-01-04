/*
 * Carrot - beacon management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

describe('Service: Events', function () {
    // load the service's module
    beforeEach(module('Carrot'));

    // instantiate service
    var event;
    beforeEach(inject(function (_Event_) {
        event = _Event_;
    }));

    it('should get the category resource promise', function () {
        expect(event).toBeDefined();
        expect(event.get()).toBeDefined();
        expect(event.query()).toBeDefined();
        expect(event.save()).toBeDefined();
    });
});
