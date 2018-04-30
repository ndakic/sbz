/**
 * Created by daka on 4/30/18.
 */

(function() {
    'use strict';

    angular
        .module('SBZApp')
        .controller('BillDetailsCtrl', BillDetailsCtrl);

    BillDetailsCtrl.$inject = ['$scope', 'entity'];

    function BillDetailsCtrl($scope, entity) {
        var vm = this;
        vm.bill = entity;

    }
})();
