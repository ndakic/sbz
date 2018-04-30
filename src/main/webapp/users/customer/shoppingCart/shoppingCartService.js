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

        var shoppingCartCount = 0;
        var shoppingBillStatus = false;


        service.shoppingCart = shoppingCart;
        service.shoppingCartCount = shoppingCartCount;
        service.shoppingBillStatus = shoppingBillStatus;

        return service;

    }
})();