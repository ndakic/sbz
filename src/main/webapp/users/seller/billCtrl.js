/**
 * Created by dakamadafaka on 7/26/17.
 */

(function (angular) {
    angular.module('SBZApp')
        .controller('billCtrl', function($scope, $log, AuthenticationService, $http, Alertify, $state){
            var vm = this;
            vm.checkBill = checkBill;
            vm.rejectBill = rejectBill;

            $scope.bills = [];

            var loadBills = function () {
                var promise = $http.get("/api/bill/all");
                promise.then(function (response) {
                    $scope.bills = response.data;
                    console.log("Bills Loaded");
                });
            };

            loadBills();

            function checkBill(bill) {
                var promise = $http.post("/api/bill/check_bill", bill);
                promise.then(function (response) {
                    if(response.status == '200'){
                        Alertify.success("Bill Accepted!");
                        loadBills();
                    }else{
                        Alertify.success("No enough articles!")
                    }

                });
            };

            function rejectBill(bill) {
                var promise = $http.post("/api/bill/reject_bill", bill);
                promise.then(function (response) {
                    if(response.status == '200'){
                        Alertify.success("Bill Rejected!");
                        loadBills();
                    }else{
                        Alertify.success("Error!")
                    }

                });
            };

        });
}(angular));