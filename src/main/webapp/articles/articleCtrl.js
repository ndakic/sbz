/**
 * Created by dakamadafaka on 7/19/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('articleCtrl', function($scope, $log, AuthenticationService, $http, Alertify){

            $scope.articles = [];

            var loadArticles = function () {
                var promise = $http.get("/api/article/all");
                promise.then(function (response) {
                    $scope.articles = response.data;
                });
            };

            loadArticles();

        });
}(angular));