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
                authorities: ['seller', 'manager']
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
                authorities: ['customer', 'seller', 'manager']
            },
            views: {
                'content@': {
                    templateUrl: 'users/seller/billHistory.html',
                    controller: 'billHistoryCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('bill-detail', {
            parent: 'app',
            url: '/bill/{id}',
            data: {
                authorities: ['customer', 'seller', 'manager'],
                pageTitle: 'Article'
            },
            views: {
                'content@': {
                    templateUrl: 'users/seller/bill-details.html',
                    controller: 'BillDetailsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Bill', function($stateParams, Bill) {
                    return Bill.get({id : $stateParams.id});
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'article',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        }).state('orders', {
            parent: 'app',
            url: '/orders',
            data: {
                authorities: ['seller']
            },
            views: {
                'content@': {
                    templateUrl: 'users/seller/order.html',
                    controller: 'orderCtrl',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();