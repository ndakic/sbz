/**
 * Created by dakamadafaka on 7/27/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('billHistoryCtrl', function($scope, $log, AuthenticationService, $http){
            var vm = this;
            vm.user = AuthenticationService.getCurrentUser();

            var loadHistory = function () {
                var promise = $http.get("/api/bill/history/" + vm.user.username);
                promise.then(function (response) {
                    $scope.bills = response.data;
                    console.log($scope.bills);
                    console.log("History Loaded");
                });
            };

            loadHistory();

        });
}(angular));