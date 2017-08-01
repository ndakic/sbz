/**
 * Created by dakamadafaka on 8/1/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('orderCtrl', function($scope, $log, AuthenticationService, $http, Alertify, $state){
            var vm = this;
            vm.orderMore = orderMore;

            $scope.articles = [];

            var loadArticles = function () {
                var promise = $http.get("/api/article/orders");
                promise.then(function (response) {
                    $scope.articles = response.data;
                    console.log("Articles Loaded");
                });
            };

            loadArticles();

            function orderMore(art) {
                console.log(art);
                var promise = $http.post("/api/article/order_more", art);
                promise.then(function (response) {
                    console.log(response);
                    if(response.status == '200'){
                        Alertify.success((art.orderQuantity + art.min - art.amount) + " articles has been ordered!");
                        loadArticles();

                    }else{
                        Alertify.success("Error!")
                    }

                });
            }

        });
}(angular));