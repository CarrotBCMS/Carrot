'use strict';

describe('Service: Apps', function () {
    // load the service's module
    beforeEach(module('Carrot'));

    // instantiate service
    var app;
    beforeEach(inject(function (_App_) {
        app = _App_;
    }));

    it('should get the category resource promise', function () {
        expect(app).toBeDefined();
        expect(app.get()).toBeDefined();
        expect(app.query()).toBeDefined();
        expect(app.save()).toBeDefined();
    });
});
