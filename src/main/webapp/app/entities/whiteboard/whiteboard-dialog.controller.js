(function() {
    'use strict';

    angular
        .module('spookywhiteboardApp')
        .controller('WhiteboardDialogController', WhiteboardDialogController);

    WhiteboardDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Whiteboard', 'SpookyMonster'];

    function WhiteboardDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Whiteboard, SpookyMonster) {
        var vm = this;

        vm.whiteboard = entity;
        vm.clear = clear;
        vm.save = save;
        vm.spookymonsters = SpookyMonster.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.whiteboard.id !== null) {
                Whiteboard.update(vm.whiteboard, onSaveSuccess, onSaveError);
            } else {
                Whiteboard.save(vm.whiteboard, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('spookywhiteboardApp:whiteboardUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
