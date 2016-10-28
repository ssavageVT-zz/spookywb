(function() {
    'use strict';

    angular
        .module('savickApp')
        .controller('SpookyMonsterDialogController', SpookyMonsterDialogController);

    SpookyMonsterDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SpookyMonster', 'Whiteboard'];

    function SpookyMonsterDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SpookyMonster, Whiteboard) {
        var vm = this;

        vm.spookyMonster = entity;
        vm.clear = clear;
        vm.save = save;
        vm.whiteboards = Whiteboard.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.spookyMonster.id !== null) {
                SpookyMonster.update(vm.spookyMonster, onSaveSuccess, onSaveError);
            } else {
                SpookyMonster.save(vm.spookyMonster, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('savickApp:spookyMonsterUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
