/**
 * Created by daka on 4/29/18.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .factory('Article', Article);

    Article.$inject = ['$resource'];

    function Article ($resource) {
        var resourceUrl =  'api/article/:id';

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