(function() {
    'use strict';
    angular
        .module('savickApp')
        .factory('Whiteboard', Whiteboard);

    Whiteboard.$inject = ['$resource'];

    function Whiteboard ($resource) {
        var resourceUrl =  'api/whiteboards/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
