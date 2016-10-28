(function() {
    'use strict';

    angular
        .module('spookywhiteboardApp')
        .controller('WhiteboardController', WhiteboardController);

    WhiteboardController.$inject = ['$scope', '$state', 'Whiteboard'];

    function WhiteboardController ($scope, $state, Whiteboard) {
        var vm = this;
        
        vm.whiteboards = [];

        loadAll();

        function loadAll() {
            Whiteboard.query(function(result) {
                vm.whiteboards = result;
            });
        }
    }
})();
