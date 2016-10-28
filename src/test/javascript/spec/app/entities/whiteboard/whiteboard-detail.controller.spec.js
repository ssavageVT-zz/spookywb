'use strict';

describe('Controller Tests', function() {

    describe('Whiteboard Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockWhiteboard, MockSpookyMonster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockWhiteboard = jasmine.createSpy('MockWhiteboard');
            MockSpookyMonster = jasmine.createSpy('MockSpookyMonster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Whiteboard': MockWhiteboard,
                'SpookyMonster': MockSpookyMonster
            };
            createController = function() {
                $injector.get('$controller')("WhiteboardDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'savickApp:whiteboardUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
