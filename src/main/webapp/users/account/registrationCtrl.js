/**
 * Created by dakamadafaka on 7/7/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('registrationCtrl', function($scope, $log, AuthenticationService, $http, $state, Alertify){

            $scope.new_user={};
            $scope.roles = ["customer", "seller", "manager"];
            $scope.categories = [];

            $scope.registration = function() {
                var promise = $http.post("/api/user/registration", $scope.new_user);
                promise.then(function (response) {
                    //console.log(response.da);
                    if(response.status == "200"){
                        Alertify.success('User created!');
                        $state.go('login');
                    }

                    else{
                        Alertify.error('Username already exist!');
                    }

                });
            };

            var loadCategories = function () {
                var promise = $http.get("/api/user/categories");
                promise.then(function (response) {
                    $scope.categories = response.data;
                });
            };

            loadCategories();

        });
}(angular));