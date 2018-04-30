/**
 * Created by daka on 4/30/18.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .factory('Bill', Bill);

    Bill.$inject = ['$resource'];

    function Bill ($resource) {
        var resourceUrl =  'api/bill/:id';

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