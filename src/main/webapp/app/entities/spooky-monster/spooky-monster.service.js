(function() {
    'use strict';
    angular
        .module('savickApp')
        .factory('SpookyMonster', SpookyMonster);

    SpookyMonster.$inject = ['$resource'];

    function SpookyMonster ($resource) {
        var resourceUrl =  'api/spooky-monsters/:id';

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
