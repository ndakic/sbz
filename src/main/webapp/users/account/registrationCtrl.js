/**
 * Created by dakamadafaka on 7/7/17.
 */

(function (angular) {
    angular.module('registration',['ui.validate'])
        .controller('registrationCtrl', function($scope, $log, AuthenticationService, $http, $state, Alertify){

            $scope.new_user={};
            $scope.roles = ["customer", "seller", "manager"];
            $scope.categories = [];

            $scope.password = '';
            $scope.confirm_password = '';

            //console.log(uiValidate);

            $scope.registration = function() {

                if($scope.password == $scope.confirm_password && $scope.password != ''){

                    $scope.new_user.password = $scope.password;

                    var promise = $http.post("/api/user/registration", $scope.new_user);
                    promise.then(function (response) {
                        if(response.status == "200"){
                            Alertify.success('User created!');
                            $state.go('login');
                        }

                        else{
                            Alertify.error('Username already exist!');
                        }

                    });

                };


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