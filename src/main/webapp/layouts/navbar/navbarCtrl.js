/**
 * Created by dakamadafaka on 7/7/17.
 */

(function() {
    'use strict';

    angular
        .module('SBZApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', '$scope', 'ShoppingCartService'];

    function NavbarController ($state, $scope, ShoppingCartService) {

        var vm = this;

        $scope.ShoppingCartService = ShoppingCartService;

        // $scope.$watch('ShoppingCartService.shoppingCart.items.length', function() {
        //     alert('hey, myVar has changed!');
        // }, true);

        //console.log("Navbar shopping cart count:", $scope.shoppingCartCount);

        //vm.$state = $state;

        vm.login = login;
        vm.registration = registration;
        vm.account = account;
        vm.articles = articles;
        vm.articles_new = articles_new;
        vm.bills = bills;
        vm.billsHistory = billsHistory;
        vm.userCategory = userCategory;
        vm.articleCategory = articleCategory;
        vm.orders = orders;
        vm.events = events;
        vm.shoppingCart = shoppingCart;


        function login() {
            $state.go('login');
        }

        function registration() {
            $state.go('registration');
        }

        function account() {
            $state.go('account');
        }

        function articles() {
            $state.go('articles');
        }

        function articles_new() {
            $state.go('articleNew');
        }

        function bills() {
            $state.go('bills');
        }

        function billsHistory() {
            $state.go('billHistory');
        }

        function userCategory() {
            $state.go('user_category');
        }

        function articleCategory() {
            $state.go('article_category');
        }

        function orders() {
            $state.go("orders");
        }

        function events() {
            $state.go("events");
        }

        function shoppingCart() {
            $state.go("shoppingCart");
        }

    }
})();
