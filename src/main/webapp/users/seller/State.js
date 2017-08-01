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
        }).state('billHistory', {
            parent: 'app',
            url: '/bill/history',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/seller/billHistory.html',
                    controller: 'billHistoryCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('orders', {
            parent: 'app',
            url: '/orders',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/seller/order.html',
                    controller: 'orderCtrl',
                    controllerAs: 'vm'
                }
            }
        });;
    }
})();