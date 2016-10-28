(function() {
    'use strict';

    angular
        .module('savickApp')
        .controller('SpookyMonsterDetailController', SpookyMonsterDetailController);

    SpookyMonsterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'SpookyMonster', 'Whiteboard'];

    function SpookyMonsterDetailController($scope, $rootScope, $stateParams, previousState, entity, SpookyMonster, Whiteboard) {
        var vm = this;

        vm.spookyMonster = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('savickApp:spookyMonsterUpdate', function(event, result) {
            vm.spookyMonster = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
