/**
 * Created by dakamadafaka on 7/20/17.
 */


(function (angular) {
    angular.module('SBZApp')
        .controller('articleNewCtrl', function($scope, $log, AuthenticationService, $http, Alertify, $state){
            var vm = this;
            $scope.article = {};
            $scope.categories = [];

            vm.newArticle = newArticle;

            var loadCategories = function () {
                var promise = $http.get("/api/category/all");
                promise.then(function (response) {
                    $scope.categories = response.data;
                });
            };

            loadCategories();

            function newArticle() {
                var promise = $http.post("/api/article/add", $scope.article);
                promise.then(function (response) {
                    console.log(response);
                    if(response.status == '200'){
                        $state.go("articles");
                    }

                });
            }

        });
}(angular));