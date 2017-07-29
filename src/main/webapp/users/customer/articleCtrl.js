/**
 * Created by dakamadafaka on 7/19/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('articleCtrl', function($scope, $log, AuthenticationService, $http, Alertify, $state){

            var vm = this;
            vm.addToShoppingCart = addToShoppingCart;
            vm.removeItem = removeItem;
            vm.countTotal = countTotal;
            vm.bill = bill;
            vm.confirmBill = confirmBill;
            vm.user = AuthenticationService.getCurrentUser();
            $scope.confirmBill = false;

            $scope.articles = [];

            $scope.shoppingCart = {
                items:[],
                receivedPoints: 0.0,
                spentPoints:0.0
            };

            console.log($scope.shoppingCart);

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

            function removeItem(art) {
                var index = $scope.shoppingCart.items.indexOf(art);
                $scope.shoppingCart.items.splice(index, 1);
            };

            function bill() {

                $scope.shoppingCart.buyer = {username: vm.user.username};

                var promise = $http.post("/api/article/bill", $scope.shoppingCart);
                promise.then(function (response) {
                    console.log("Done bill!");
                    $scope.shoppingCart = response.data;
                    console.log($scope.shoppingCart);

                    $scope.confirmBill = true;

                    if($scope.shoppingCart.buyer.userProfile.points >= $scope.shoppingCart.spentPoints){
                        $scope.shoppingCart.finalPrice -= $scope.shoppingCart.spentPoints;
                        $scope.shoppingCart.buyer.userProfile.points -= $scope.shoppingCart.spentPoints;
                    }else{
                        $scope.shoppingCart.spentPoints = 0.0;
                    }
                });
            };

            function confirmBill() {
                console.log($scope.shoppingCart);

                var promise = $http.post("/api/article/submit_bill", $scope.shoppingCart);
                promise.then(function (response) {
                    console.log("Done! submit bill");
                    Alertify.success("Bill successfully created!");
                    $scope.shoppingCart = response.data;
                    $state.go("billHistory");
                });

            };

        });
}(angular));