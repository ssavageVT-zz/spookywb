(function() {
    'use strict';

    angular
        .module('spookywhiteboardApp')
        .controller('WhiteboardDeleteController',WhiteboardDeleteController);

    WhiteboardDeleteController.$inject = ['$uibModalInstance', 'entity', 'Whiteboard'];

    function WhiteboardDeleteController($uibModalInstance, entity, Whiteboard) {
        var vm = this;

        vm.whiteboard = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Whiteboard.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
