/**
 * Created by dakamadafaka on 7/19/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('articleCtrl', function($scope, $log, AuthenticationService, $http, Alertify){

            var vm = this;
            vm.addToShoppingCart = addToShoppingCart;
            vm.countTotal = countTotal;
            vm.bill = bill;
            vm.user = AuthenticationService.getCurrentUser();

            $scope.articles = [];

            $scope.shoppingCart = {
                items:[],
                receivedPoints: 0.0,
                spentPoints:0.0
            };

            var loadArticles = function () {
                var promise = $http.get("/api/article/all");
                promise.then(function (response) {
                    $scope.articles = response.data;
                });
            };

            loadArticles();

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
                for (var i = 0; i < $scope.shoppingCart.items.length; i++) {
                    if ($scope.shoppingCart.items[i].article.id == art.id) {
                        alertify.log("Article " + art.title + " is already added!");
                        return
                    }
                }

                var item = {
                    article: art,
                    price: art.price,
                    quantity: 1

                };

                Alertify.success("Article " + art.title+ " is added to Shopping Cart!");
                $scope.shoppingCart.items.push(item);

            };

            function bill() {

                $scope.shoppingCart.buyer = {username: vm.user.username};

                var promise = $http.post("/api/article/bill", $scope.shoppingCart);
                promise.then(function (response) {
                    console.log("Done!");
                    $scope.shoppingCart = response.data;
                    console.log($scope.shoppingCart);
                });
            }


        });
}(angular));