/*
 * Carrot - beacon content management
 * Copyright (C) 2016 Heiko Dreyer
 */

'use strict';

describe('Service: Beacons', function () {
    // load the service's module
    beforeEach(module('Carrot'));

    // instantiate service
    var beacon;
    beforeEach(inject(function (_Beacon_) {
        beacon = _Beacon_;
    }));

    it('should get the category resource promise', function () {
        expect(beacon).toBeDefined();
        expect(beacon.get()).toBeDefined();
        expect(beacon.query()).toBeDefined();
        expect(beacon.save()).toBeDefined();
    });
});
