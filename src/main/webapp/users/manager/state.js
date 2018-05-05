/**
 * Created by dakamadafaka on 7/30/17.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('article_category', {
            parent: 'app',
            url: '/article_category',
            data: {
                authorities: ['manager']
            },
            views: {
                'content@': {
                    templateUrl: 'users/manager/articleCategory.html',
                    controller: 'categoryCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('user_category', {
            parent: 'app',
            url: '/user_category',
            data: {
                authorities: ['manager']
            },
            views: {
                'content@': {
                    templateUrl: 'users/manager/userCategory.html',
                    controller: 'categoryCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('events', {
            parent: 'app',
            url: '/events',
            data: {
                authorities: ['manager']
            },
            views: {
                'content@': {
                    templateUrl: 'users/manager/event.html',
                    controller: 'eventCtrl',
                    controllerAs: 'vm'
                }
            }
        });
    }
})();