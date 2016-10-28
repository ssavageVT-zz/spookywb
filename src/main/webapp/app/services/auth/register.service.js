(function () {
    'use strict';

    angular
        .module('spookywhiteboardApp')
        .factory('Register', Register);

    Register.$inject = ['$resource'];

    function Register ($resource) {
        return $resource('api/register', {}, {});
    }
})();
