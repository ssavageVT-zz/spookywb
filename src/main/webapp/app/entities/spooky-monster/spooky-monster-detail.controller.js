(function() {
    'use strict';

    angular
        .module('spookywhiteboardApp')
        .controller('SpookyMonsterDetailController', SpookyMonsterDetailController);

    SpookyMonsterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SpookyMonster', 'Whiteboard'];

    function SpookyMonsterDetailController($scope, $rootScope, $stateParams, previousState, entity, SpookyMonster, Whiteboard) {
        var vm = this;

        vm.spookyMonster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('spookywhiteboardApp:spookyMonsterUpdate', function(event, result) {
            vm.spookyMonster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
