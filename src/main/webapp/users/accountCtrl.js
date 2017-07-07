/**
 * Created by dakamadafaka on 7/7/17.
 */

(function (angular) {
    angular.module('account',['authentication'])
        .controller('accountCtrl', function($scope, $log, AuthenticationService, $http, $state, previousState){

            var vm = this;
            vm.previousState = previousState.name;
            vm.user = AuthenticationService.getCurrentUser();

            function account() {
                var promise = $http.get("/api/user/" + vm.user.username);
                promise.then(function (response) {
                    $scope.account = response.data;
                });
            };

            account();

        });
}(angular));