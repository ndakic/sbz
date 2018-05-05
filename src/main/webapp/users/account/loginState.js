/**
 * Created by dakamadafaka on 3/1/17.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('login', {
            parent: 'app',
            url: '/login',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/account/login.html',
                    controller: 'loginCtrl'
                }
            }
        }).state('registration', {
            parent: 'app',
            url: '/registration',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/account/registration.html',
                    controller: 'registrationCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('account', {
            parent: 'app',
            url: '/account',
            data: {
                authorities: ['customer', 'seller', 'manager']
            },
            views: {
                'content@': {
                    templateUrl: 'users/account/account.html',
                    controller: 'accountCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'event',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        });
    }
})();
