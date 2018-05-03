/**
 * Created by dakamadafaka on 7/7/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('accountCtrl', function($scope, $log, AuthenticationService, $http, $state, previousState, Alertify){

            var vm = this;
            vm.previousState = previousState.name;
            vm.user = AuthenticationService.getCurrentUser();
            vm.update = update;

            function account() {
                var promise = $http.get("/api/user/" + vm.user.username);
                promise.then(function (response) {
                    console.log("SRBIJA!");
                    $scope.account = response.data;
                    console.log($scope.account);
                });
            };

            account();

            function update() {
                var promise = $http.post("/api/user/update", $scope.account);
                promise.then(function (response) {
                    console.log("Done bill!");
                    $scope.account = response.data;
                    Alertify.success("User data successfully changed!");
                });
            };

        });
}(angular));