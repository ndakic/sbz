/**
 * Created by daka on 4/30/18.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .factory('Bill', Bill);

    Bill.$inject = ['$resource', '$state'];

    function Bill ($resource, $state) {
        var resourceUrl =  'api/bill/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {

                        data = angular.fromJson(data);

                        if(data.status == 500){
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