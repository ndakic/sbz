/**
 * Created by dakamadafaka on 7/30/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('categoryCtrl', function($scope, $log, AuthenticationService, $http, Alertify, $state){
            var vm = this;
            vm.newArtCategory = newArtCategory;
            vm.deleteArtCat = deleteArtCat;

            $scope.artCategory = {};

            var loadArticleCategories = function () {
                var promise = $http.get("/api/category/articles");
                promise.then(function (response) {
                    $scope.articleCategories = response.data.slice(1,response.data.length);
                });
            };

            loadArticleCategories();


            function newArtCategory() {
                var promise = $http.post("/api/category/article/add", $scope.artCategory);
                promise.then(function (response) {
                    console.log(response);
                    if(response.status == '200'){
                        loadArticleCategories();
                        Alertify.success("Category successfully added.");
                    };
                });
            };

            function deleteArtCat(category){
              console.log(category);
                var promise = $http.post("/api/category/article/delete/" + category.id);
                promise.then(function (response) {
                    console.log(response);
                    if(response.status == '200'){
                        loadArticleCategories();
                        Alertify.success("Category successfully deleted.");
                    };
                });

            };

        });
}(angular));