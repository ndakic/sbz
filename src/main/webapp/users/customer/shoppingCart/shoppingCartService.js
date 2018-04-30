/**
 * Created by daka on 4/29/18.
 */


(function () {
    'use strict';
    angular
        .module('shopService',[])
        .factory('ShoppingCartService', ShoppingCartService);

    ShoppingCartService.$inject = ['$resource'];

    function ShoppingCartService() {

        var service = {};

        var shoppingCart = {
            items:[],
            receivedPoints: 0.0,
            spentPoints:0.0
        };


        service.shoppingCart = shoppingCart;

        return service;

    }
})();