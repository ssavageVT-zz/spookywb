'use strict';

describe('Controller Tests', function() {

    describe('SpookyMonster Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockSpookyMonster, MockWhiteboard;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockSpookyMonster = jasmine.createSpy('MockSpookyMonster');
            MockWhiteboard = jasmine.createSpy('MockWhiteboard');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'SpookyMonster': MockSpookyMonster,
                'Whiteboard': MockWhiteboard
            };
            createController = function() {
                $injector.get('$controller')("SpookyMonsterDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'spookywhiteboardApp:spookyMonsterUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
