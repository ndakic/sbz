/**
 * Created by dakamadafaka on 3/1/17.
 */

(function() {
    'use strict';
    angular
        .module('WEBApp')
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
                    templateUrl: 'users/login.html',
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
                    templateUrl: 'users/registration.html',
                    controller: 'registCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('account', {
            parent: 'app',
            url: '/account',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/account.html',
                    controller: 'accountCtrl',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();
