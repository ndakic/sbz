/**
 * Created by daka on 4/29/18.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .factory('Article', Article);

    Article.$inject = ['$resource', '$state'];

    function Article ($resource, $state) {
        var resourceUrl =  'api/article/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);

                        if(data["id"] == -1){
                            $state.go("404");
                        }
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();