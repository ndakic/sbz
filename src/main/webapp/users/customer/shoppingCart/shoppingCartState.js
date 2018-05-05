/**
 * Created by daka on 4/29/18.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('shoppingCart', {
            parent: 'app',
            url: '/shoppingCart',
            data: {
                authorities: ['customer']
            },
            views: {
                'content@': {
                    templateUrl: 'users/customer/shoppingCart/shoppingCart.html',
                    controller: 'articleCtrl',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();