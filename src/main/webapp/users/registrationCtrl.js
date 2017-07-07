/**
 * Created by dakamadafaka on 7/7/17.
 */

(function (angular) {
    angular.module('registration',['authentication'])
        .controller('registrationCtrl', function($scope, $log, AuthenticationService, $http, $state, Alertify){

            $scope.new_user={};

            $scope.registration = function() {
                var promise = $http.post("/api/user/registration", $scope.new_user);
                promise.then(function (response) {

                    if(response.data == "success"){
                        Alertify.success('User created!');
                        $state.go('login');
                    }

                    if(response.data == "fail"){
                        Alertify.error('Username already exist!');
                    }

                });
            };

        });
}(angular));