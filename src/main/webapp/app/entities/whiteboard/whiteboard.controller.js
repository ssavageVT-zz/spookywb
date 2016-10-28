(function() {
    'use strict';

    angular
        .module('savickApp')
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
