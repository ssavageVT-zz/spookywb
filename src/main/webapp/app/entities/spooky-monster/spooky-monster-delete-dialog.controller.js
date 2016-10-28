(function() {
    'use strict';

    angular
        .module('savickApp')
        .controller('SpookyMonsterDeleteController',SpookyMonsterDeleteController);

    SpookyMonsterDeleteController.$inject = ['$uibModalInstance', 'entity', 'SpookyMonster'];

    function SpookyMonsterDeleteController($uibModalInstance, entity, SpookyMonster) {
        var vm = this;

        vm.spookyMonster = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SpookyMonster.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
