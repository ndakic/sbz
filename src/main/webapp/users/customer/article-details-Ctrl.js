/**
 * Created by daka on 4/30/18.
 */

(function() {
    'use strict';

    angular
        .module('SBZApp')
        .controller('ArticleDetailsCtrl', ArticleDetailsCtrl);

    ArticleDetailsCtrl.$inject = ['$scope', 'entity', 'previousState'];

    function ArticleDetailsCtrl($scope, entity, previousState) {
        var vm = this;

        vm.previousState = previousState.name;

        vm.article = entity;


    }
})();
