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
                authorities: ['customer', 'seller', 'manager']
            },
            views: {
                'content@': {
                    templateUrl: 'users/customer/articles.html',
                    controller: 'articleCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('articleNew', {
            parent: 'app',
            url: '/article_new',
            data: {
                authorities: ['seller']
            },
            views: {
                'content@': {
                    templateUrl: 'users/customer/articleNew.html',
                    controller: 'articleNewCtrl',
                    controllerAs: 'vm'
                }
            }
        }).state('article-detail', {
            parent: 'app',
            url: '/articles/{id}',
            data: {
                authorities: ['customer', 'seller', 'manager'],
                pageTitle: 'Article'
            },
            views: {
                'content@': {
                    templateUrl: 'users/customer/article-details.html',
                    controller: 'ArticleDetailsCtrl',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'Article', function($stateParams, Article) {
                    return Article.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'article-detail',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        }).state('404', {
            parent: 'app',
            url: '/404',
            data: {
                authorities: ['customer', 'seller', 'manager']
            },
            views: {
                'content@': {
                    templateUrl: 'errors/404.html',
                    controller: 'articleCtrl',
                    controllerAs: 'vm'
                }
            }
        })
    }
})();