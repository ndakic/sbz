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
                    templateUrl: 'articles/article.html',
                    controller: 'articleCtrl'
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
                    templateUrl: 'articles/articleNew.html',
                    controller: 'articleNewCtrl',
                    controllerAs: 'vm'
                }
            }
        })
    }
})();