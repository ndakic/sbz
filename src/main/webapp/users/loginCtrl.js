/**
 * Created by dakamadafaka on 3/1/17.
 */
(function (angular) {
    angular.module('login',['authentication'])
        .controller('loginCtrl', function($scope, $log, AuthenticationService, $http){
            $scope.user={};
            $scope.login=function () {
                AuthenticationService.login($scope.user.username, $scope.user.password, loginCbck);
            };
            function loginCbck(success) {
                if (success) {
                    $log.info('success!');
                }
                else{
                    $log.info('failure!');
                }
            };

            function checkAppDB() {

                $http.get('/rest/user/checkApp')
                    .then(function (response) {
                        $scope.users = response.data;
                    })
                    .catch(function (data, status) {
                        console.log('error!', data,status);
                    });
            }

            checkAppDB();
        });
}(angular));