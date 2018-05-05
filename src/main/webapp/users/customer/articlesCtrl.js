/**
 * Created by dakamadafaka on 7/19/17.
 */

(function() {
    'use strict';

    angular
        .module('SBZApp')
        .controller('articleCtrl', articleCtrl);

        articleCtrl.$inject = ['$scope', '$http', '$state', 'AuthenticationService', 'Alertify', 'ShoppingCartService'];

        function articleCtrl ($scope, $http, $state, AuthenticationService, Alertify, ShoppingCartService ) {





            var vm = this;
            vm.addToShoppingCart = addToShoppingCart;
            vm.removeItem = removeItem;
            vm.countTotal = countTotal;
            vm.bill = bill;
            vm.confirmBill = confirmBill;
            vm.user = AuthenticationService.getCurrentUser();
            vm.search = search;

            $scope.ShoppingCartService = ShoppingCartService;
            $scope.confirmBill = ShoppingCartService.shoppingBillStatus;
            $scope.articles = [];
            $scope.events = [];
            $scope.shoppingCart = ShoppingCartService.shoppingCart;
            $scope.searchedTerm = "";


            function search() {
                if($scope.searchedTerm != ''){
                    var promise = $http.get("/api/article/search/" + $scope.searchedTerm);
                    promise.then(function (response) {
                        $scope.articles = response.data;
                    });
                };
            };

            if($scope.confirmBill){
                bill();
            };

            var loadArticles = function () {
                var promise = $http.get("/api/article/all");
                promise.then(function (response) {
                    $scope.articles = response.data;
                });
            };

            loadArticles();


            var loadEvents = function () {
                var promise = $http.get("/api/event/all");
                promise.then(function (response) {
                    $scope.events = response.data;
                });
            };

            loadEvents();

            $scope.$watch('searchedTerm', function() {
                if($scope.searchedTerm == ''){
                    loadArticles();
                }
            }, true);

            $scope.$watch('shoppingCart', function() {
                countTotal();
            }, true);

            function countTotal() {
                $scope.total = 0;

                var price = 0;
                for(var i in $scope.shoppingCart.items){
                    price += $scope.shoppingCart.items[i].price * $scope.shoppingCart.items[i].quantity ;
                }

                $scope.total = price;
            };


            function addToShoppingCart(art) {
                for (var i = 0; i < ShoppingCartService.shoppingCart.items.length; i++) {
                    if (ShoppingCartService.shoppingCart.items[i].article.id == art.id) {
                        alertify.log("Article " + art.title + " is already added!");

                        return;
                    }
                }

                var item = {
                    article: art,
                    price: art.price,
                    quantity: 1

                };

                Alertify.success("Article " + art.title+ " is added to Shopping Cart!");
                ShoppingCartService.shoppingCart.items.push(item);

                $scope.ShoppingCartService.shoppingCartCount++;

            };

            function removeItem(art) {
                var index = ShoppingCartService.shoppingCart.items.indexOf(art);
                ShoppingCartService.shoppingCart.items.splice(index, 1);
                $scope.ShoppingCartService.shoppingCartCount--;
            };

            function bill() {

                $scope.shoppingCart.buyer = {username: vm.user.username};

                var promise = $http.post("/api/article/bill", $scope.shoppingCart);
                promise.then(function (response) {
                    $scope.shoppingCart = response.data;

                    ShoppingCartService.shoppingBillStatus = true;
                    $scope.confirmBill = ShoppingCartService.shoppingBillStatus;

                    if($scope.shoppingCart.buyer.userProfile.points >= $scope.shoppingCart.spentPoints){
                        $scope.shoppingCart.finalPrice -= $scope.shoppingCart.spentPoints;
                        $scope.shoppingCart.buyer.userProfile.points -= $scope.shoppingCart.spentPoints;
                    }else{
                        $scope.shoppingCart.spentPoints = 0.0;
                    }
                });
            };

            function confirmBill() {

                var promise = $http.post("/api/article/submit_bill", $scope.shoppingCart);
                promise.then(function (response) {

                    Alertify.success("Bill successfully created!");
                    $scope.shoppingCart = response.data;
                    $state.go("billHistory");
                });

            };

        }

})();