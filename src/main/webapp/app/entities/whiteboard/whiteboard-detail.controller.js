(function() {
    'use strict';

    angular
        .module('savickApp')
        .controller('WhiteboardDetailController', WhiteboardDetailController);

    WhiteboardDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Whiteboard', 'SpookyMonster'];

    function WhiteboardDetailController($scope, $rootScope, $stateParams, previousState, entity, Whiteboard, SpookyMonster) {
        var vm = this;

        vm.whiteboard = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('savickApp:whiteboardUpdate', function(event, result) {
            vm.whiteboard = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
