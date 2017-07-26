/**
 * Created by dakamadafaka on 7/19/17.
 */

(function() {
    'use strict';
    angular
        .module('SBZApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider.state('articles', {
            parent: 'app',
            url: '/articles',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/customer/article.html',
                    controller: 'articleCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('articleNew', {
            parent: 'app',
            url: '/article_new',
            data: {
                authorities: []
            },
            views: {
                'content@': {
                    templateUrl: 'users/customer/articleNew.html',
                    controller: 'articleNewCtrl',
                    controllerAs: 'vm'
                }
            }
        })
    }
})();