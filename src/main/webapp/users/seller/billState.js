/**
 * Created by dakamadafaka on 7/26/17.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('bills', {
            parent: 'app',
            url: '/bills',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/seller/bill.html',
                    controller: 'billCtrl',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();