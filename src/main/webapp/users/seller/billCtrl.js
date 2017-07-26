/**
 * Created by dakamadafaka on 7/26/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('billCtrl', function($scope, $log, AuthenticationService, $http, Alertify){
            var vm = this;

            $scope.bills = [];

            var loadBills = function () {
                var promise = $http.get("/api/bill/all");
                promise.then(function (response) {
                    $scope.bills = response.data;
                    console.log("Bills Loaded");
                    console.log($scope.bills[0]);
                });
            };

            loadBills();

        });
}(angular));