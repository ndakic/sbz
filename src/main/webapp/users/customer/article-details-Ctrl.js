/**
 * Created by daka on 4/30/18.
 */

(function() {
    'use strict';

    angular
        .module('SBZApp')
        .controller('ArticleDetailsCtrl', ArticleDetailsCtrl);

    ArticleDetailsCtrl.$inject = ['$scope', 'entity'];

    function ArticleDetailsCtrl($scope, entity) {
        var vm = this;

        vm.article = entity;


    }
})();
